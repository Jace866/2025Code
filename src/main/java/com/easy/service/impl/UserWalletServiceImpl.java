package com.easy.service.impl;

import com.easy.common.WebConfig;
import com.easy.dao.UserWalletDao;
import com.easy.entity.User;
import com.easy.entity.UserWallet;
import com.easy.service.BusinessService;
import com.easy.service.ThreadService;
import com.easy.service.UserService;
import com.easy.service.UserWalletService;
import com.easy.util.HttpUtils;
import com.easy.vo.InterfaceBean;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class UserWalletServiceImpl implements UserWalletService {
    private static String APIUrl = "";//https://web001-universe-portal-3-api.nappynat.net/three";
    private static final String balanceUrl = "/front/three/lo/balance";
    private static final String deductionUrl = "/front/three/lo/deduction";
    private static final String paymentUrl = "/front/three/lo/payment";
    private static final String checkPaymentStatusUrl = "/front/three/lo/checkPaymentStatus";
    private static final String syncOrdersUrl = "/front/three/lo/syncOrders";
    private static final String[] TRANS_TYPE = {"BET","WIN","REFUND","CASHOUT","CANCEL_DEDUCT","CANCEL_RETURN","SETTLEMENT_ROLLBACK_RETURN","SETTLEMENT_ROLLBACK_DEDUCT"};
    private static DecimalFormat decFmt=new DecimalFormat("#0.00");
    @Autowired
    private UserWalletDao userWalletDao;

    private UserService userService;

    private ThreadService threadService;

    private BusinessService businessService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }
    @Autowired
    public void setBusinessService(@Lazy BusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public void save(UserWallet wallet) {
        userWalletDao.insert(wallet);
    }

    @Override
    public BigDecimal queryBalance(int uid) {
        return userWalletDao.queryBalance(uid);
    }

    @Override
    public void updateBalanceByUserId(double balance, int uid) {
        userWalletDao.updateBalanceByUserId(balance, uid);
    }

    private InterfaceBean getInterface(InterfaceBean param){
        Integer mid = param.getId();
        String loginname = param.getLoginname();
        String password = param.getPassword();
        String currencyType = param.getCurrencyType();
        if(mid==null||mid==0 || loginname == null || "".equals(loginname) || password == null || "".equals(password) || currencyType == null || "".equals(currencyType)) {
            User rec = userService.findUserById(param.getUid());
            if(rec!=null){
                mid = rec.getMerchantId();
                loginname = rec.getName();
                password = rec.getLoginPwd();
                Integer currencyId = rec.getCurrencyId();
                currencyType = businessService.getCurrencyType(currencyId);
                param.setId(mid);
                param.setPid(businessService.getMerchantPid(mid));
                param.setLoginname(loginname);
                param.setPassword(password);
                param.setCurrencyType(currencyType);
            }
        }

        return param;
    }

    //定义为 private, 只允许从工厂方法中创建对象
    private JSONObject getHttpParam(InterfaceBean param){
        param = getInterface(param);
        APIUrl = businessService.getMerchantWalletApiAddress(param.getId());//Db.queryStr("SELECT wallet_aip_address FROM sys_merchant WHERE provider=?", param.getPid());
        if(APIUrl==null || "".equals(APIUrl)) APIUrl = "https://test8765-0.zhengwudalian.com/three";
        JSONObject obj = new JSONObject();
        obj.put("pid", param.getPid());
        obj.put("loginname", param.getLoginname());                                                    //会员账号，最长64位
        obj.put("password", param.getPassword());
        obj.put("currencyType", param.getCurrencyType());
        if( param.getAmount()!=null ) obj.put("amount", param.getAmount().toString());
        if( param.getBillno()!=null ) obj.put("billno", param.getBillno());                            //注单号
        if( param.getTransactionId()!=null ) obj.put("transactionId", param.getTransactionId());       //业务订单号
        //转账类型;下注BET,派彩WIN,退款REFUND, 提前结算CASHOUT,订单取消补扣 CANCEL_DEDUCT,订单取消返 CANCEL_RETURN,结算回滚返还SETTLEMENT_ROLLBACK_RETURN,结算回滚补扣SETTLEMENT_ROLLBACK_DEDUCT
        if( param.getTransferType()!=null ) obj.put("transferType", TRANS_TYPE[param.getTransferType()]);
        return obj;
    }
    @Override
    public String getTransType(int index){
        return TRANS_TYPE[index];
    }

    private void updateBalance(Integer uid, String currencyType, String balance){
        if(uid != null){
            String tableBey = this.getTableBey(currencyType);
            userWalletDao.updateBalanceBytableBey(tableBey, balance, uid);
        }
    }

    /**
     * 获取余额对应字段
     * @param currencyType
     * @return
     */
    public String getTableBey(String currencyType){
        String tableBey = "balance";
        if("USDT".equals(currencyType)){
            tableBey = "usdt_balance";
        }
        return tableBey;
    }

    /**
     * 查询余额
     * @return
     */
    @Override
    public String selBalance(InterfaceBean param){
        Integer status = WebConfig.getStatus();
        String money = (status == null || !NumberUtils.isNumber(String.valueOf(status)) ? 1 : Integer.parseInt(String.valueOf(status))) == 0 ? "1000.00":"0.00";
        JSONObject paramJson = getHttpParam(param);
        //System.out.println("param="+paramJson);
        try{
            String jsonStr = HttpUtils.doPost(APIUrl+balanceUrl, paramJson);
            //System.out.println(jsonStr);
            if(jsonStr!=null && jsonStr.startsWith("{") && jsonStr.endsWith("}")){
                Map paramsMap = new Gson().fromJson(jsonStr,Map.class);
                JSONObject jsonObj = JSONObject.fromObject(paramsMap);
                if(paramsMap.get("code")!=null && jsonObj.getInt("code")==0){
                    JSONArray jsonArr = jsonObj.getJSONArray("data");
                    if(jsonArr.size()>0){
                        for(int i=0; i < jsonArr.size(); i++){
                            JSONObject json = jsonArr.getJSONObject(i);
                            Double balance = json.getDouble("balance");
                            money = decFmt.format(balance);
                        }
                    }
                }
            }
            updateBalance(param.getUid(), param.getCurrencyType(), money);
        }catch (Exception e){
            //e.printStackTrace();
            threadService.addRunLog(ThreadServiceImpl.RUN_LOG_TYPE_API_INVOKE,"查询余额单一钱包请求异常，错误数据："+e.getMessage());
            updateBalance(param.getUid(), param.getCurrencyType(), money);
            return money;
        }

        return money;
    }
}
