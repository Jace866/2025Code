package com.easy.service;

import com.easy.entity.LotteryInfo;

import java.util.List;
import java.util.Map;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
public interface GameHelpService {

    Map<String,Integer> getCommMap(int lotteryId);

    Map<String,String> getNameMap(int lotteryId);

    Map<String,String> getLottMap();

    String getLotteryCode(int lotteryId);

    String getLotteryName(int lotteryId);

    Map<String,String> getLottNameMap();

    List<LotteryInfo> getLotteryList();

    String getLotteryCode();
}
