package com.easy.controller;

import com.easy.common.Result;
import com.easy.entity.SysCurrencyType;
import com.easy.entity.SysMerchant;
import com.easy.entity.TestUser;
import com.easy.service.*;
import com.easy.util.*;
import com.easy.vo.UserVo;
import net.sf.json.JSONArray;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@RestController
public class ForwardGameCtrl {

    public static String gameUrl; //当前打开游戏域名

    private BusinessService businessService;

    private UserService userService;

    private SysParameterService sysParameterService;

    private ThreadService threadService;

    private TestUserService testUserService;

    private SysCurrencyTypeService sysCurrencyTypeService;

    @Autowired
    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setSysParameterService(SysParameterService sysParameterService) {
        this.sysParameterService = sysParameterService;
    }
    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }
    @Autowired
    public void setTestUserService(TestUserService testUserService) {
        this.testUserService = testUserService;
    }
    @Autowired
    public void setSysCurrencyTypeService(SysCurrencyTypeService sysCurrencyTypeService) {
        this.sysCurrencyTypeService = sysCurrencyTypeService;
    }

    //API请求白名单验证
    private boolean apiVerify(String pid){
        HttpServletRequest request = IpAddressUtil.getHttpServletRequest();
        String loginIp = IpAddressUtil.getIpAddress(request);
        String rurl = request.getRequestURL().toString();
        String domain = rurl.substring(0, rurl.indexOf("/",8));
        domain = domain.replaceAll("http://", "").replaceAll("https://", "");
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
        if(pid==null){
            return new Result<>().error("-1", "缺少pid参数");
        }
        //API验证
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

        String maintenance_flag=sysParameterService.getParamValue("sys_param", "maintenance_flag");
        if( "1".equals(maintenance_flag) && !"".equals(pid)){
            String maintenance_msg=sysParameterService.getParamValue("sys_param", "maintenance_msg");
            if( maintenance_msg!=null && !"".equals(maintenance_msg.trim())){
                return new Result<>().error("-1", maintenance_msg);
            }else{
                return new Result<>().error("-1", "系统正在进行升级维护... ...稍后再访问");
            }
        }
        SysMerchant res = businessService.getCagent(pid);
        if(res==null){
            return new Result<>().error("-5", "商户账号错误");
        }
        int merchantId = res.getId();
        String des_key=res.getDesKey();
        String md5_key=res.getMd5Key();
        int type = res.getApiType();
        String prefixName=res.getPrefixName(); //前缀名称
        int tranFlag=res.getTranFlag();        //判断是否支持额度转换
        if(prefixName==null)prefixName="";
        if(type != 0){
            return new Result<>().error("-1", "无权限访问");
        }
        String sign = MD5Utils.md5(pid + params + md5_key);
        //签名验证
        if(sign.equals(key)){
            try {
                String targetParams = DesUtility.decrypt(params, des_key);
                targetParams=targetParams.replace("/\\\\/","&");
                MultiMap multiMap = new MultiMap();
                UrlEncoded.decodeTo(targetParams, multiMap, "UTF-8");
                //System.out.println(multiMap.toString());
                //检测参数是否确定
                //String cp = BusinessService.checkParameter(multiMap, type==0?"openGame":"playGame");
                String cp = businessService.checkParameter(multiMap, "playGame");
                if(cp!=null){
                    return new Result<>().error("-1",cp);
                }

                String cagent=res.getProvider();
                String domain=res.getDomain();
                String game=res.getGame();
                String gameType = multiMap.getString("gametype");
                String way = multiMap.getString("way");
                String oddtype =  multiMap.getString("oddtype");

                if(gameType==null||"".equals(gameType)||oddtype==null||"".equals(oddtype)){
                    return new Result<>().error("-1","进入游戏失败,缺少参数");
                }
                gameType = gameType.toUpperCase();
                if(!(","+game+",").contains(","+gameType+",")){
                    return new Result<>().error("-1","进入游戏失败");
                }

                if(res.getGameId()==null || "".equals(res.getGameId())){
                    return new Result<>().error("-1","进入游戏失败");
                }

                if (way == null || "".equals(way)) {
                    way = "2";
                }
                String[] domains = domain.split(",");
                if("1".equals(way)){
                    //电脑版域名
                    String inGameUrl = sysParameterService.getParamValue("sys_param", "in_game_url");
                    if(inGameUrl==null || "".equals(inGameUrl)){
                        domain=domains[0];
                    }else{
                        domain = inGameUrl;
                    }
                }else{
                    //手机版域名
                    String inGameUrl = sysParameterService.getParamValue("sys_param", "line_detection");
                    if(inGameUrl==null || "".equals(inGameUrl)){
                        domain=domains[domains.length-1];
                    }else{
                        domain = inGameUrl;
                    }
                }

                //二，提供API给第三方商户调用，需要账号和密码验证
                String loginname = /*cagent + */multiMap.getString("loginname");
                if (!loginname.matches("(?:(?:[A-Za-z0-9])){6,15}")) {
                    return new Result<>().error("-1", "账号不符合规则，必须是6-15位数字和字母组合，请重新输入");
                }
                UserVo rec = userService.findUserAndCurrencyType(merchantId, loginname);
                if (rec == null) {
                    return new Result<>().error("-9", "账号或密码错误");
                } else {
                    String password = multiMap.getString("password");
                    String actype = multiMap.getString("actype");   //0-试玩,1-真钱
                    if (!",0,1,".contains(","+actype+",")) {
                        return new Result<>().error("-1", "登录失败");
                    }
                    password = userService.doEncrypt(loginname, password);
                    if (!password.equals(rec.getLoginPwd())) {
                        return new Result<>().error("-1", "登录失败,账号或密码错误");
                    }
                    int uid = rec.getId();
                    int tryFlag = rec.getTestFlag();//0-正式账号,1-测试账号
                    String loginIp = IpAddressUtil.getIpAddress(request);
                    String token = CommonUtil.getUuid();
                    //int tryFlag=0; //0-正式,2-试玩

                    //试玩游戏
                    double money=50000;
                    if("0".equals(actype)){
                        String showDemoBut = sysParameterService.getParamValue3("sys_param", "show_demo_button");
                        if( showDemoBut!=null && "0".equals(showDemoBut)){
                            return new Result<>().error("-1", "抱歉，不允许试玩");
                        }

                        tryFlag=2;
                        String sessionId=request.getSession().getId();
                        TestUser user = testUserService.findUserBySessionId(sessionId);
                        if( user==null ){
                            String quota=sysParameterService.getParamValue3("sys_param", "test_quota");
                            try{
                                if( quota!=null && !"".equals(quota.trim())){
                                    money=Integer.parseInt(quota);
                                }
                            }catch(Exception e){}
                            user = new TestUser();
                            user.setSessionId(sessionId);
                            user.setBalance(BigDecimal.valueOf(money));
                            user.setBetMoney(BigDecimal.valueOf(0));
                            user.setLoginIp(loginIp);
                            user.setLoginTime(new Date());
                            testUserService.save(user);
                        }
                        uid= user.getId();
                        loginname = "test_"+uid;
                    }else{
                        if(res.getIsCurrency()==1){
                            String currencyType = multiMap.getString("currencyType");
                            if(currencyType==null||"".equals(currencyType))currencyType="CNY";
                            //登入更新币种
                            SysCurrencyType ctRec = sysCurrencyTypeService.findByCurrencyType(currencyType);
                            if(ctRec == null){
                                return new Result<>().error("-1", "登录失败,暂不支持"+currencyType+"币种");
                            }
                            userService.updateCurrencyIdById(ctRec.getId(), uid);
                        }

                        if(",A,B,C,".contains(","+oddtype+",")) {
                            userService.updatePtypeById(oddtype, uid);
                        }
                    }

                    //把前面的登录踢出
                    userService.kickoutByUid(uid);
                    //UserService.addOnlineUser(1,this.getSession().getId(), uid, loginname, tryFlag, Integer.parseInt(way), "", "", merchantId, token);
                    threadService.addLoginLog(uid, loginname, tryFlag, "", "", "API登录成功(" + loginname + ")", "", 1, merchantId, request.getSession(), token, null, way);

                    if("0".equals(actype)){
                        loginname = "guest";
                    }
                    //做跳转处理
                    domain = domain.replaceAll("https://", "").replaceAll("http://", "");
                    String https_enable = sysParameterService.getParamValue2("sys_param", "https_enable");
                    String protocol = "1".equals(https_enable) ? "https://" : "http://";
                    String param = "actype="+actype+"&";
                    if(multiMap.get("dm")!=null && !"".equals(multiMap.get("dm"))){
                        param = "dm=" + multiMap.get("dm") + "&";
                    }
                    String s = token;
                    if (multiMap.get("gametype") != null) {
                        param += "game=" + multiMap.get("gametype") + "&";
                        if (multiMap.get("code") != null) {
                            param += "code=" + multiMap.get("code") + "&";
                            s+="&"+multiMap.get("code");
                        }
                    }
                    domains = domain.split(",");

                    //新增线路检测功能
                    if(gameUrl!=null && !"".equals(gameUrl)){
                        String isLine = sysParameterService.getParamValue("sys_param", "show_sport_notice");
                        if("1".equals(isLine)){
                            domains = gameUrl.split(",");
                        }
                    }

                    for(int i=0; i<domains.length; i++){
                        domains[i] = protocol+domains[i];
                    }
                    domain = JSONArray.fromObject(domains).toString();
                    param += "token=" + token;

                    String showDemoButton = sysParameterService.getParamValue("sys_param", "show_demo"); //1为开启H5前后端分开
                    boolean flag = "1".equals(showDemoButton)&&!"1".equals(way)?true:false;  //way 1-电脑，其它-手机
                    String hostUrl = flag?"/#/playgame/":"/playgame?";
                    param = flag?s:param+"&sign="+MD5Utils.md5(pid + prefixName + loginname + uid + actype);
                    if(domains.length>1){
                        hostUrl = String.format("{\"url\":\"%s%s\", \"hosts\":%s,\"checkApi\":\"/api/linefind\"}", hostUrl, param, domain);
                    }else{
                        hostUrl =String.format("{\"url\":\"%s%s%s\"}", domains[0], hostUrl, param);
                    }
                    return new Result<>().success(hostUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new Result<>().error("-4","系统出错");
            }
        }else{
            return new Result<>().error("-12","签名失败");
        }
    }
}
