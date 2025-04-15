package com.easy.service;
import com.easy.entity.ChatPlat;
import com.easy.vo.BankcodeEntity;
import com.easy.vo.ItemBean;
import com.easy.vo.LotteryBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
public interface SysParameterService {

    //刷新系统参数
    Map<String,Map<String,String>> refreshParameter(String type);

    String getParamValue(String type, String code);

    String getParamValue2(String type, String code);

    String getParamValue3(String type, String code);

    Map<String,String> getParamMap(String type);

    Map<String, ItemBean> getItemBeanMap();

    void initLotteryMap();

    LotteryBean getLotteryBeanById(Integer lotteryId);

    LotteryBean getLotteryBeanByCode(String lotteryCode);

    Map<Integer,LotteryBean> getAllLotteryBean();

    LotteryBean getLotteryBeanMerchantById(String lotteryId);

    List<LotteryBean> getLotteryBeanList();

    List<BankcodeEntity> getUserBankcodeList();

    //查询平台信息
    ChatPlat getPlatInfo(String platCode);

    void updateValue(int value, String type, String code);
}
