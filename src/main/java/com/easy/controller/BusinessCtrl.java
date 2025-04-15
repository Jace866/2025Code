package com.easy.controller;

import com.easy.common.Result;
import com.easy.entity.SysMerchant;
import com.easy.entity.UserWallet;
import com.easy.service.BusinessService;
import com.easy.service.LotteryInfoService;
import com.easy.service.UserService;
import com.easy.service.UserWalletService;
import com.easy.util.DateUtil;
import com.easy.util.DesUtility;
import com.easy.util.IpAddressUtil;
import com.easy.util.MD5Utils;
import com.easy.vo.InterfaceBean;
import com.easy.vo.LotteryInitVo;
import com.easy.vo.ReportEntity;
import com.easy.vo.UserVo;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@RestController
@RequestMapping(value = "/businessprocess")
public class BusinessCtrl {

    private static Map<String, Long> lastAccessTime = new HashedMap();
    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private LotteryInfoService lotteryInfoService;

    private boolean access(String ip) {
        long currentTime = System.currentTimeMillis();
        // 判断时间间隔是否大于一分钟
        if(lastAccessTime.get(ip)==null){
            lastAccessTime.put(ip, Long.valueOf(0));
        }
        boolean flag = false;
        if (currentTime - lastAccessTime.get(ip) < 30 * 1000) {
            // 如果小于一分钟，则返回错误信息或者直接抛出异常
            //throw new RuntimeException("接口访问过于频繁，请稍后再试");
            flag=true;
        } else {
            // 如果大于一分钟，则更新上一次访问时间，并执行接口逻辑
            lastAccessTime.put(ip, currentTime);
        }
        return flag;
    }

    //API请求白名单验证
    private boolean apiVerify(String pid){
        HttpServletRequest request = IpAddressUtil.getHttpServletRequest();
        String loginIp = IpAddressUtil.getIpAddress(request);
        String rurl = request.getRequestURL().toString();
        String domain = rurl.substring(0, rurl.indexOf("/",8));
        domain = domain.replaceAll("http://", "").replaceAll("https://", "");
        //System.out.println(pid+","+loginIp+","+domain);
        return businessService.queryByMerchantDomainAndIp(pid, domain,loginIp);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Result<Object> index(@RequestParam(value = "params", defaultValue = "") String params,
                                @RequestParam(value = "sign") String key,
                                @RequestParam(value = "pid") String pid){
        HttpServletRequest request = IpAddressUtil.getHttpServletRequest();
        String isPost = request.getMethod();
        if(!"POST".equals(isPost)){
            return new Result<>().error("-1", "拒绝访问，请用POST请求");
        }
        if(pid==null || "".equals(pid)){
            return new Result<>().error("-1", "缺少pid参数");
        }
        if(apiVerify(pid)){
            String loginIp = IpAddressUtil.getIpAddress(request);
            return new Result<>().error("-11", "拒绝访问，请求域名地址不正确或IP["+loginIp+"]不在白名单");
        }
        if(params==null || "".equals(params)){
            return new Result<>().error("-1", "缺少params参数");
        }
        if(key==null || "".equals(key)){
            return new Result<>().error("-1", "缺少sign参数");
        }
        params = params.replace(" ","+");

        //1. 查询商家
        SysMerchant res = businessService.getCagent(pid);
        if(res==null){
            return new Result<>().error("-5", "商户账号错误");
        }
        int merchantId = res.getId();      //id
        String des_key=res.getDesKey();   //DES加密密钥
        String md5_key=res.getMd5Key();   //MD5加密密钥
        String provider=res.getProvider(); //商家ID
        int type=res.getApiType();        //API商家类型
        String domain= res.getDomain();
        String sign = MD5Utils.md5(pid + params + md5_key);

        //签名验证
        if(sign.equals(key)){
            try {
                String maintenanceTime = res.getMaintenanceTime();
                if(maintenanceTime!=null && !"".equals(maintenanceTime)){
                    String nowtime = DateUtil.nowtime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = sdf.parse(nowtime);
                    Date date2 = sdf.parse(maintenanceTime);
                    if(date1.compareTo(date2) < 0){
                        return new Result<>().error("-110", "维护中");
                    }
                }

                //2. 获取请求参数
                String targetParams = DesUtility.decrypt(params, des_key);
                //targetParams=targetParams.replace("/\\\\/","&");
                MultiMap multiMap = new MultiMap();
                UrlEncoded.decodeTo(targetParams, multiMap, "UTF-8");
                if(multiMap.get("method")==null){
                    return new Result<>().error("-1", "缺少method参数");
                }
                String method = multiMap.getString("method");
                //3. 检测参数是否确定
                String cp = businessService.checkParameter(multiMap, method);
                if(cp!=null){
                    return new Result<>().error("-1", cp);
                }

                //公用部分
                if("report".equals(method)){
                    //7. 投注报表批量查询和注单捞单
                    String loginIp= IpAddressUtil.getIpAddress(request);
                    if(!"TEST9988".equals(provider)&&access(loginIp)){
                        return new Result<>().error("-11", "接口访问过于频繁，请稍后再试");
                    }

                    int userId = res.getUserId();
                    String begin = multiMap.getString("beginDate");
                    String end = multiMap.getString("endDate");
                    List<ReportEntity> recList = new ArrayList<>();

                    if(!DateUtil.isDateVail(begin)||!DateUtil.isDateVail(end)){
                        return new Result<>().success("0","success", recList);
                    }

                    long day = DateUtil.getDaysOfTowDiffDate(begin, DateUtil.nowtime());
                    if(day>6){
                        return new Result<>().error("-11", "只能获取6天以内的数据");
                    }

                    long minutes = DateUtil.getMinutesOfTowDiffDate(begin, end);
                    if(minutes>30){
                        return new Result<>().error("-11", "获取日期必须在30分钟范围内");
                    }

                    recList = businessService.getUserPlayList(merchantId, userId, begin, end);
                    return new Result<>().success("0","success", recList);
                }else if("verifyorder".equals(method)){
                    //7. 订单捞单回调验证是否成功,如果验证失败下一次捞单还会返回该订单信息
                    String  billno= multiMap.getString("billno");
                    String[] billnoArr = billno.split(",");
                    String result = businessService.VerifOrderStatus( billnoArr);
                    if (result == null) {
                        return new Result<>().error("-6", "订单号不存在");
                    }
                    return new Result<>().success("验证成功", result);
                }else if("codes".equals(method)){
                    //8. 获取游戏ID
                    String codes=res.getGameId();
                    List<LotteryInitVo> recList = lotteryInfoService.getLotteryListByGameIds(codes);
                    List<JSONObject> ibList = new ArrayList<>();
                    for(LotteryInitVo rec : recList){
                        JSONObject json = new JSONObject();
                        json.put("code", rec.getLotteryId());
                        json.put("name", rec.getLotteryName());
                        ibList.add(json);
                    }
                    return new Result<>().success(ibList);
                }

                //二，提供API给第三方商户调用，需要账号和密码验证
                String loginname = multiMap.getString("loginname");//provider+multiMap.getString("loginname");
                if( !loginname.matches("(?:(?:[A-Za-z0-9])){6,15}")){
                    return new Result<>().error("-7", "账号不符合规则，必须是6-15位数字和字母组合，请重新输入");
                }
                String password = multiMap.getString("password");
                if(password==null || "".equals(password)){
                    return new Result<>().error("-7", "密码不能为空");
                }

                // 8. 会员账号请求信息处理
                boolean isReg=false;
                UserVo rec = userService.findUserAndCurrencyType(merchantId, loginname);
                if(rec == null){
                    //9. 会员注册
                    String oddtype =  multiMap.get("oddtype")==null?"":multiMap.getString("oddtype"); //盘口
                    String testFlag = multiMap.getString("actype");  //注册类型，0为正式会员，1为测试会员
                    String way = multiMap.getString("way");
                    String currencyType = multiMap.getString("currencyType");
                    if(currencyType==null||"".equals(currencyType)) currencyType="CNY";
                    // 检测账号是否存在与创建账号);
                    String loginIp= IpAddressUtil.getIpAddress(request);
                    String aguser = res.getAgent();
                    if(aguser==null || "".equals(aguser)){
                        return new Result<>().error("-2", "注册失败");
                    }
                    isReg = businessService.createUserName(loginname, password, oddtype, currencyType, merchantId, testFlag, aguser, loginIp, way);
                    if(!isReg){
                        return new Result<>().error("-2", "注册失败");
                    }else{
                        rec = userService.findUserAndCurrencyType(merchantId, loginname);
                    }
                }

                //返回操作注册接口
                if("reg".equals(method)) {
                    if(isReg){
                        return new Result<>().success("注册成功", "{\"currencyType\":\""+rec.getCurrencyType()+"\"}");
                    }else{
                        return new Result<>().error("-3", "注册失败, 账号已存在");
                    }
                }

                password = userService.doEncrypt(loginname,password);
                if(!password.equals(rec.getLoginPwd())){
                    return new Result<>().error("-9", "账号或密码错误");
                }

                int uid = rec.getId();
                if("bal".equals(method)){
                    if(res.getIsBal()==1){
                        int isTran = businessService.getIsTran(rec.getMerchantId());
                        //判断0为单一钱包,1为额度转换
                        if(isTran==0){
                            InterfaceBean param = new InterfaceBean();
                            param.setId(rec.getMerchantId());
                            param.setUid(uid);
                            param.setLoginname(rec.getName());
                            param.setPassword(rec.getLoginPwd());
                            param.setCurrencyId(rec.getCurrencyId());
                            String balance = userWalletService.selBalance(param);
                            String result = "{\"balance\":\""+balance+"\",\"domain\":\""+domain+"\"}";
                            return new Result<>().success("", result);
                        }else{
                            double balance =businessService.QueryBalance(uid);
                            //10. 查询账号余额
                            return new Result<>().success("success", "{\"balance\":\""+balance+"\",\"domain\":\""+domain+"\"}");
                        }
                    }else{
                        return new Result<>().error("-1", "不支持查询余额功能", "{\"balance\":\"0\"}");
                    }
                }else if("tran".equals(method)){
                    //11. 额度转账
                    if(res.getIsTran()==1){
                        JSONObject result = businessService.CreditTrans(uid, multiMap, type, merchantId);
                        if(result.getInt("code")==0){
                            return new Result<>().success("成功", result.getString("data"));
                        }else{
                            return new Result<>().error("-1", result.getString("msg"));
                        }
                    }else{
                        return new Result<>().error("-1", "不支持额度转账功能");
                    }
                }else if("qos".equals(method)){
                    //6. 游戏额度转换订单查询
                    if(res.getIsTran()==1){
                        String  billno= multiMap.getString("billno");
                        String result = businessService.QueryOrderStatus(uid, billno);
                        if (result == null) {
                            return new Result<>().error("-6", "订单号不存在");
                        }else{
                            return new Result<>().success("查询成功", result);
                        }
                    }else{
                        return new Result<>().error("-6", "不支持额度转换订单查询功能");
                    }
                }else{
                    return new Result<>().error("-1", "缺少参数");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new Result<>().error("-4", "系统出错");
            }
        }else{
            return new Result<>().error("-12", "签名失败");
        }
    }
}
