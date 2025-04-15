package com.easy.service.impl;

import com.easy.common.WebConfig;
import com.easy.entity.*;
import com.easy.service.*;
import com.easy.vo.LotteryBean;
import com.easy.vo.ReportEntity;
import com.easy.vo.SysMerchantVo;
import com.easy.vo.UserPlayVo;
import net.sf.json.JSONObject;
import org.eclipse.jetty.util.MultiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import com.easy.util.DateUtil;
import com.easy.util.SequenceUtil;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class BusinessServiceImpl implements BusinessService {
    public final static int STATUS_RUNNING = 1; //额度转换状态_进行中
    public final static int STATUS_SUCCESS = 2; //额度转换状态_成功
    public final static int STATUS_INSUFFICIENT_BALANCE = 3;   //额度转换状态_余额不足
    public final static int STATUS_ERROR = 4;   //额度转换状态_失败
    public final static int T_DEPOSIT = 142;    //转入, 转入成功后会员余额减少
    public final static int T_WITHDRAWAL = 242;//转出, 转出成功后会员余额增加
    private final static Map<Integer, String> REMARK_MAP=new HashMap<Integer,String>();
    private static Map<String, String> MERCHANT_MAP=new HashMap<String,String>();
    private static Map<Integer, String> MERCHANT_PID_MAP=new HashMap<Integer,String>();
    private static Map<Integer, Integer> ISTRAN_MAP=new HashMap<Integer,Integer>();		 //是否开启转换功能1-开启，默认为0-单一钱包
    private static Map<Integer, String> CURRENCYTYPE_ID_MAP=new HashMap<Integer,String>();
    private final static String ODDTYPE=",A,B,C,";
    static{
        REMARK_MAP.put(T_DEPOSIT, "额度转入");
        REMARK_MAP.put(T_WITHDRAWAL, "额度转出");
    }

    private UserPlayService userPlayService;

    private UserService userService;

    private UserWalletService userWalletService;

    private UserInfoExtService userInfoExtService;

    private ChatRoleService chatRoleService;

    private SysMerchantService sysMerchantService;

    private SysParameterService sysParameterService;

    private SysCurrencyTypeService sysCurrencyTypeService;

    private UserTransactionService userTransactionService;

    private UserTransactionApiService userTransactionApiService;

    private UserTransactionApiLogService userTransactionApiLogService;

    private GameHelpService gameHelpService;

    private LotteryItemService lotteryItemService;
    @Autowired
    public void setUserPlayService(UserPlayService userPlayService) {
        this.userPlayService = userPlayService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserWalletService(@Lazy UserWalletService userWalletService) {
        this.userWalletService = userWalletService;
    }
    @Autowired
    public void setUserInfoExtService(UserInfoExtService userInfoExtService) {
        this.userInfoExtService = userInfoExtService;
    }
    @Autowired
    public void setChatRoleService(ChatRoleService chatRoleService) {
        this.chatRoleService = chatRoleService;
    }
    @Autowired
    public void setSysMerchantService(SysMerchantService sysMerchantService) {
        this.sysMerchantService = sysMerchantService;
    }
    @Autowired
    public void setSysParameterService(SysParameterService sysParameterService) {
        this.sysParameterService = sysParameterService;
    }
    @Autowired
    public void setSysCurrencyTypeService(SysCurrencyTypeService sysCurrencyTypeService) {
        this.sysCurrencyTypeService = sysCurrencyTypeService;
    }
    @Autowired
    public void setUserTransactionService(UserTransactionService userTransactionService) {
        this.userTransactionService = userTransactionService;
    }
    @Autowired
    public void setUserTransactionApiService(UserTransactionApiService userTransactionApiService) {
        this.userTransactionApiService = userTransactionApiService;
    }
    @Autowired
    public void setUserTransactionApiLogService(UserTransactionApiLogService userTransactionApiLogService) {
        this.userTransactionApiLogService = userTransactionApiLogService;
    }
    @Autowired
    public void setGameHelpService(GameHelpService gameHelpService) {
        this.gameHelpService = gameHelpService;
    }
    @Autowired
    public void setLotteryItemService(LotteryItemService lotteryItemService) {
        this.lotteryItemService = lotteryItemService;
    }

    //获取数据源配置名称， 此名称在 com/easy/config/WebConfig.java 文件配置 ActiveRecordPlugin arp = new ActiveRecordPlugin("myCfgName",druidPlugin);
    @Override
    public String getCfgName(){
        return "myCfgName";
    }

    // 参数检查
    @Override
    public String checkParameter(MultiMap paramMap, String method) {
        String[] needParams = {};
        /*if("register".equals(method)){
            needParams = new String[]{"loginname","password"};
        }else if("login".equals(method)){
            needParams = new String[]{"loginname","password"};
        }else if(",userinfo,balance,".contains(","+method+",")) {
//            needParams = new String[]{"token"};
            needParams = new String[]{"loginname"};
        }else if(",transfer,".contains(","+method+",")) {
            needParams = new String[]{"type","loginname"};
        }else if("reg".equals(method)){ 							// 检测账号是否存在与创建账号
//            needParams = new String[]{"loginname","password","actype","oddtype"};
            needParams = new String[]{"loginname","password"};
        }else if("bal".equals(method)){ 							// 查询账号余额
            needParams = new String[]{"loginname","password"};   //actype 0-试玩,1-真钱
        }else if("tran".equals(method)){ 							// 额度转账
            needParams = new String[]{"loginname","password","type","billno","credit"};
        }else if("qos".equals(method)){ 							// 查询订单状态
            needParams = new String[]{"cagent","billno","method"};
        }else if("report".equals(method)){ 						// 查询投注报表
            needParams = new String[]{"beginDate","endDate"};
        }else if("verifyorder".equals(method)){ // 验证是否已成功捞单
            needParams = new String[]{"billno"};
        }else if("playGame".equals(method)){
            needParams = new String[]{"loginname","password","actype","oddtype", "gameType", "dm"};
        }else if("openGame".equals(method)){
//            needParams = new String[]{"token","gameType", "dm"};
            needParams = new String[]{"loginname","password"};
        }else if("changePassword".equals(method)){
            needParams = new String[]{"loginname","newPwd", "oldPwd"};
        }else{
            return "method 参数值为空";
        }*/
        if("reg".equals(method)){ 							// 检测账号是否存在与创建账号
            needParams = new String[]{"loginname","password","actype","way"};
        }else if("qos".equals(method)){ 							// 查询订单状态
            needParams = new String[]{"billno"};
        }else if("report".equals(method)){ 						// 查询投注报表
            needParams = new String[]{"beginDate","endDate"};
        }else if("verifyorder".equals(method)){ // 验证是否已成功捞单
            needParams = new String[]{"billno"};
        }else if("playGame".equals(method)){
            needParams = new String[]{"loginname","password","actype","oddtype", "gametype"};
        }
        for (String p : needParams) {
            if (paramMap.get(p) == null)
                return p + " 参数值为空";
        }
        return null;
    }

    @Override
    public void initMerchantMap(){
        List<SysMerchantVo> list = sysMerchantService.findList();
        list.forEach(sysMerchantVo -> {
            MERCHANT_PID_MAP.put(sysMerchantVo.getId(), sysMerchantVo.getProvider());
            MERCHANT_MAP.put(sysMerchantVo.getProvider(), sysMerchantVo.getWalletAipAddress());
            ISTRAN_MAP.put(sysMerchantVo.getId(), sysMerchantVo.getIsTran());
        });
        initCurrencyTypeMap();
    }

    private void initCurrencyTypeMap(){
        List<SysCurrencyType> list = sysCurrencyTypeService.findList();
        list.forEach(sysCurrencyType -> {
            CURRENCYTYPE_ID_MAP.put(sysCurrencyType.getId(), sysCurrencyType.getType());
        });
    }

    @Override
    public String getMerchantPid(int id){
        return MERCHANT_PID_MAP.get(id);
    }
    @Override
    public String getMerchantWalletApiAddress(int id){
        String provider = getMerchantPid(id);
        return MERCHANT_MAP.get(provider);
    }
    @Override
    public Integer getIsTran(int id){
        return ISTRAN_MAP.get(id);
    }
    @Override
    public String getCurrencyType(int id){
        return CURRENCYTYPE_ID_MAP.get(id);
    }

    /**
     * 查询商户号信息
     * @param provider
     * @return
     */
    @Override
    public SysMerchant getCagent(String provider) {
        return sysMerchantService.findByStatusAndProvider(1, provider);
    }

    /**
     * 查询商户ID信息
     * @param merchantId
     * @return
     */
    @Override
    public SysMerchant getMerchantInfo(int merchantId) {
        return sysMerchantService.findByMerchantIdAndStatus(1, merchantId);
    }

    /**
     * 查询验证商户API的域名是否存在
     * @param domain
     * @return
     */
    public boolean queryByMerchantDomain(String domain) {
        SysMerchant sysMerchant = sysMerchantService.queryByMerchantDomain(domain);
        return sysMerchant != null;
    }
    /**
     * 查询验证商户API的域名和IP是否存在，不存在即拒绝访问
     * @param pid
     * @param domain
     * @param ip
     * @return
     */
    @Override
    public boolean queryByMerchantDomainAndIp(String pid, String domain, String ip) {
        ip=","+ip+",";
        SysMerchant sysMerchant = sysMerchantService.queryByMerchantDomainAndIp(pid, domain, ip);
        return sysMerchant == null;
    }

    /**
     * 查询验证商户API的域名是否存在，不存在即拒绝访问
     * @param pid
     * @param domain
     * @param ip
     * @return
     */
    @Override
    public boolean queryByMerchantDomain(String pid, String domain, String ip) {
        SysMerchant sysMerchant = sysMerchantService.queryByMerchantDomain(pid, domain, ip);
        return sysMerchant == null;
    }


    /**
     * 更新转换日志状态
     * @param uid
     * @param transNo
     * @param TranStatus
     * @param remark
     */
    @Override
    public void updateTranLog(int uid, String transNo, int TranStatus, String remark){
        userTransactionApiLogService.updateTranLog(remark, TranStatus, uid, transNo);
    }

    /**
     * 创建用户账号
     * @param username
     * @param password
     * @param oddtype
     * @param aguser 上级代理账号
     * @returnD
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean createUserName(String username, String password, String oddtype, String currencyType, int merchantId, String testFlag, String aguser, String ip, String way) {
        boolean flag = false;
        //1. 检查币种是否存在
        SysCurrencyType scRec = sysCurrencyTypeService.findByCurrencyType(currencyType);
        if(scRec==null) return flag;

        //2. 检查账号是否存在
        User userInfo = userService.findFirstByName(username);
        if(userInfo == null){
            //regcode 暂时未userId
            User superUser = userService.findFirstByTypeAndName(2, aguser);//查询代理账号
            if( superUser!=null){
                //3. 生成用户表记录
                int superId=superUser.getId();
                String nowtime = DateUtil.nowtime();
                User user = new User();
                user.setSuperId(superId).setGrade(1).setType(1).setCurrencyId(scRec.getId()).setName(username).setNickname(username)
                        .setRegfrom("&" + superUser.getName() + superUser.getRegfrom()).setRegfromid("&" + superId + superUser.getRegfromid())
                        .setLoginPwd(userService.doEncrypt(username, password)).setMemberLayer("A")
                        .setPtype(!"".equals(oddtype) && ODDTYPE.contains("," + oddtype + ",") ? oddtype : superUser.getPtype())
                        .setTestFlag(Integer.parseInt(testFlag)).setRegTime(DateUtil.strToDate(DateUtil.yyyy_MM_dd_HH_mm_ss, nowtime))
                        .setLoginIp(ip).setPlatform(String.valueOf(WebConfig.getServerId())).setRegFlag(way == null || "".equals(way) ? 2 : Integer.parseInt(way))
                        .setMerchantId(merchantId);
                userService.save(user);

                //4. 生成用户扩展表记录
                UserInfoExt userInfoExt = new UserInfoExt();
                userInfoExt.setUserId(user.getId());
                userInfoExt.setSex(9);
                int roleId = chatRoleService.queryIdByType(1);
                userInfoExt.setRoleId(roleId);
                userInfoExtService.save(userInfoExt);

                //5 .生成用户中心钱包表记录
                UserWallet wallet = new UserWallet();
                wallet.setUserId(user.getId());
                wallet.setBalance(BigDecimal.valueOf(0));
                wallet.setMoney(BigDecimal.valueOf(0));
                userWalletService.save(wallet);
                flag=true;
            }
        }
        return flag;
    }

    /**
     * 生成转换日志
     * @param uid
     * @param transNo
     * @param amount
     * @param TranType
     * @param mh5
     */
    public void GenerateTranLog(int uid, String transNo, double amount, int TranType, int mh5){
        UserTransactionApiLog userTransactionApiLog = new UserTransactionApiLog();
        userTransactionApiLog.setUserId(uid);
        userTransactionApiLog.setTransNo(transNo);
        userTransactionApiLog.setTransType(TranType);
        userTransactionApiLog.setTransTime(new Date());
        userTransactionApiLog.setTransMoney(BigDecimal.valueOf(amount));
        userTransactionApiLog.setDescription(REMARK_MAP.get(TranType));
        userTransactionApiLog.setMobileFlag(mh5);
        userTransactionApiLog.setStatus(STATUS_RUNNING);
        userTransactionApiLogService.save(userTransactionApiLog);
    }

    /**
     * 转换金额
     * @param uid
     * @param multiMap
     * @param merchantId        商户ID
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public JSONObject CreditTrans(int uid, MultiMap multiMap, int appType, int merchantId){
        JSONObject json = new JSONObject();
        json.put("code", -1);
        if(!"".equals(multiMap.get("type"))){
            String type = multiMap.getString("type");
            double money = 0;
            String txt = "";
            int transType = 0;
            int mh5 = "y".equals(multiMap.get("mh5"))?1:0;
            String nowTime = DateUtil.nowtime();
            String transNo=SequenceUtil.getTransferNo( DateUtil.getPrefix() );
            double merchantBalance = 0;
            String billno=transNo;
            if(multiMap.get("credit")!=null){
                money = TOAgMoeny(Double.valueOf(multiMap.getString("credit")));

                billno = multiMap.get("billno").toString();
                //检查订单是否已经存在
                UserTransaction userTransaction = userTransactionService.findFirstByUserIdAndrefNo(uid, billno);
                if(userTransaction!=null){
                    json.put("msg","提交的订单号已存在");
                    return json;
                }
            }
            if(appType==0){
                if(money<=0){
                    json.put("msg","转换金额不能小于0");
                    return json;
                }
                //商户账号余额
                merchantBalance = sysMerchantService.queryBalance(merchantId).doubleValue();
                if("IN".equals(type)&&merchantBalance<money){
                    json.put("msg","商户余额不足");
                    return json;
                }
            }

            //4.根据调用结果进行一步操作
            double balance = userWalletService.queryBalance(uid).doubleValue();
            double oldBalance=balance;
            String apiType="API转账";
            if("IN".equals(type)){
                balance = (balance*1000 + money*1000)/1000.0;
                //更新转换成功日志状态
                txt = "成功转入,旧额:"+oldBalance+",新额:"+balance;
                transType = T_DEPOSIT;
                if(appType == 0){
                    merchantBalance-=TOAgMoeny(money);
                }
                apiType="API转入";
            }else if("OUT".equals(type)){
                if(balance<money){
                    updateTranLog(uid, transNo, STATUS_INSUFFICIENT_BALANCE, "转出失败，余额不足");
                    json.put("msg", "余额不足");
                    return json;
                }
                balance = (balance*1000 - money*1000)/1000.0;
                txt = "成功转出,旧额:"+oldBalance+",新额:"+balance;
                transType = T_WITHDRAWAL;
                if(appType == 0) {
                    merchantBalance += TOAgMoeny(money);
                }
                apiType="API转出";
            }
            //2.生成转换日志
            GenerateTranLog(uid, transNo, money, transType, mh5);
            try {
                //4.1.1 生成额度转换(存款)的账变记录
                UserTransaction trans = new UserTransaction();
                trans.setUserId(uid);
                trans.setTransNo(transNo);
                trans.setTransType(transType);
                trans.setTransTime(DateUtil.strToDate(DateUtil.yyyy_MM_dd_HH_mm_ss, nowTime));
                trans.setTransMoney(BigDecimal.valueOf(money));
                trans.setBeforeMoney(BigDecimal.valueOf(oldBalance));
                trans.setAfterMoney(BigDecimal.valueOf(balance));
                trans.setRemark(apiType);
                if(multiMap.get("credit")!=null){
                    trans.setRefNo(billno);
                }
                userTransactionService.save(trans);

                //4.1.2 生成转换 成功的额度转换(存款)记录
                UserTransactionApi transApi = new UserTransactionApi();
                transApi.setUserId(uid);
                transApi.setTransNo(transNo);
                transApi.setTransType(transType);
                transApi.setTransTime(DateUtil.strToDate(DateUtil.yyyy_MM_dd_HH_mm_ss, nowTime));
                transApi.setTransMoney(BigDecimal.valueOf(money));
                transApi.setDescription(txt);
                transApi.setMobileFlag(mh5);
                transApi.setStatus(STATUS_SUCCESS);
                transApi.setRemark(apiType);
                userTransactionApiService.save(transApi);

                //更新余额
                userWalletService.updateBalanceByUserId(balance, uid);
                if(appType == 0){
                    sysMerchantService.updateBalanceById(merchantBalance, merchantId);
                }
                //更新转换成功日志状态
                updateTranLog(uid, transNo, STATUS_SUCCESS, txt);
            } catch (Exception e) {
                e.printStackTrace();
                //更新转换失败日志状态
                updateTranLog(uid, transNo, STATUS_ERROR, txt);
                json.put("msg", "系统出错");
                return json;
            }

            json.put("code",0);
            json.put("data","{\"balance\":\""+TOAgMoeny(balance)+"\",\"billno\":\""+billno+"\"}");
        }
        return json;
    }

    /**
     * 将金额转换为2位小数，不四舍五入
     * @param amount
     * @return
     */
    @Override
    public double TOAgMoeny(Double amount){
        return ((double)((int)(amount*100)))/100;
    }

    /**
     * 查询余额
     * @param uid
     * @return
     */
    @Override
    public double QueryBalance(int uid){
        double balance = userWalletService.queryBalance(uid).doubleValue();
        return TOAgMoeny(balance);
    }

    /**
     * 查询订单状态
     * @param uid
     * @param billno
     * @return
     */
    @Override
    public String QueryOrderStatus(int uid, String billno){
        String result = null;
        UserTransactionApiLog userTransactionApiLog = userTransactionApiLogService.findFirstByUserIdAndTransNo(uid, billno);
        if(userTransactionApiLog!=null){
            int status = userTransactionApiLog.getStatus()==2?1:(userTransactionApiLog.getStatus()==3?2:0);
            Double balance = TOAgMoeny(userTransactionApiLog.getTransMoney().doubleValue());
            String time = DateUtil.dateToStr(DateUtil.yyyy_MM_dd_HH_mm_ss, userTransactionApiLog.getTransTime());
            result = "{\"status\":"+status+",\"amount\":"+balance+",\"billno\":\""+billno+"\",\"time\":\""+time+"\"}";
        }
        return result;
    }
    /**
     * 验证订单是否捞单成功
     * @param billnoArr
     * @return
     */
    @Override
    public String VerifOrderStatus(String[] billnoArr){
        String result = null;
        for(String billno : billnoArr){
            userPlayService.updateIsEndByPlayNo(1, billno);
            result = "OKOKOK";
        }
        return result;
    }

    /**
     * 查询注单报表
     * @param merchantId
     * @param uid
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List<ReportEntity> getUserPlayList(int merchantId, int uid, String begin, String end){
        String pid = getMerchantPid(merchantId);
        List<UserPlayVo> userPlayVos = new ArrayList<>();
        //查询历史记录
        if(DateUtil.isYesToday(begin)){ //查询历史记录
            userPlayVos = userPlayService.findHistoryYesToday(begin, end);
        }else{
            userPlayVos = userPlayService.findHistoryToday(begin, end);
        }
        pid = getMerchantPid(5);
        List<ReportEntity> listReport = new ArrayList<>();
        for(UserPlayVo rec : userPlayVos){
            int lotteryId = rec.getLotteryId();
            String itemId = rec.getItemId()+"";
            String lotteryName = gameHelpService.getLotteryName(lotteryId);//Db.queryStr("select lottery_name from lottery_info where lottery_id=?", rec.getInt("lottery_id"));
            String methodName = "";
            String itemName = "";

            Map<String,String> nameMap = gameHelpService.getNameMap(lotteryId);
            LotteryBean lotteryMap = sysParameterService.getLotteryBeanById(lotteryId);
            if((lotteryId>=200&&lotteryId<300)||(lotteryId>=300&&lotteryId<400)||(lotteryId>=400&&lotteryId<500)||(lotteryId>=900&&lotteryId<1000)){
                lotteryMap = sysParameterService.getLotteryBeanMerchantById(lotteryId+"_"+merchantId);
            }
            if(nameMap.get(itemId)!=null){
                String[] arrName = nameMap.get(itemId).split("#");
                if(arrName.length>1){
                    methodName = arrName[0];
                    String itemCode = rec.getItemCode();
                    if("HX".equals(itemCode)){
                        itemName = rec.getContent();
                    }else if(",LX,LM,LW,".contains(","+itemCode.substring(0,2)+",")){
                        String gameItemName = lotteryItemService.queryItemName(lotteryMap.getType(), itemCode);
                        if (gameItemName == null || "".equals(gameItemName)) {
                            itemName = rec.getContent();
                        }else{
                            int num = rec.getNum();
                            String str = num>1?"复式"+num+"组 ":"";
                            itemName = String.format("%s[%s%s] ", gameItemName.replaceFirst(methodName, "").trim(), str, rec.getContent());
                        }
                    }else if("ZXBZ".equals(itemCode)){
                        String[] nums = rec.getContent().split(",");
                        itemName = nums.length+"不中["+rec.getContent()+"]";
                    }else{
                        itemName = arrName[1];
                    }
                }
            }
            ReportEntity report = new ReportEntity();
            report.setPid(pid);
            report.setLoginName(rec.getName());
            report.setPassword("");
            report.setCurrencyType(rec.getCurrencyType());
            report.setBillno(rec.getPlayNo());
            report.setPlayTime(rec.getPlayTime());
            report.setIssue(rec.getIssue());
            report.setPtype(rec.getPtype());
            report.setStatus(rec.getStatus());
            report.setLotteryId(lotteryId);
            report.setLotteryName(lotteryName);
            report.setMethodName(methodName);
            report.setItemId(rec.getItemId());
            report.setItemCode(rec.getItemCode());
            report.setItemName(itemName);
            report.setContent(rec.getContent());
            report.setNum(rec.getNum());
            report.setMoney(rec.getMoney().doubleValue());
            report.setValidBet(rec.getValidBet().doubleValue());
            report.setRatio(rec.getRatio().doubleValue());
            report.setRatio2(rec.getRatio2().doubleValue());
            report.setBonus(rec.getBonus().doubleValue());
            report.setWins(rec.getWins().doubleValue());
            report.setPct(rec.getPct().doubleValue());
            report.setComm(rec.getComm().doubleValue());
            report.setOpenCode(rec.getOpenCode());
            report.setOpenTime(rec.getOpenTime()==null?"": rec.getOpenTime());
            report.setTransactionId(rec.getTransactionId());
            listReport.add(report);
        }
        return listReport;
    }
}
