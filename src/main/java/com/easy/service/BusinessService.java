package com.easy.service;

import com.easy.entity.SysMerchant;
import com.easy.vo.ReportEntity;
import net.sf.json.JSONObject;
import org.eclipse.jetty.util.MultiMap;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
public interface BusinessService {

    String getCfgName();

    // 参数检查
    String checkParameter(MultiMap paramMap, String method);

    void initMerchantMap();

    String getMerchantPid(int id);

    String getMerchantWalletApiAddress(int id);

    Integer getIsTran(int id);

    String getCurrencyType(int id);

    SysMerchant getCagent(String provider);

    SysMerchant getMerchantInfo(int merchantId);

    boolean queryByMerchantDomainAndIp(String pid, String domain, String ip);

    boolean queryByMerchantDomain(String pid, String domain, String ip);

    void updateTranLog(int uid, String transNo, int TranStatus, String remark);

    boolean createUserName(String username, String password, String oddtype, String currencyType, int merchantId, String testFlag, String aguser, String ip, String way);

    JSONObject CreditTrans(int uid, MultiMap multiMap, int appType, int merchantId);

    double TOAgMoeny(Double amount);

    double QueryBalance(int uid);

    String QueryOrderStatus(int uid, String billno);

    String VerifOrderStatus(String[] billnoArr);

    List<ReportEntity> getUserPlayList(int merchantId, int uid, String begin, String end);
}
