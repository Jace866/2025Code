package com.easy.service;

import com.easy.entity.LotteryInfo;
import com.easy.vo.LotteryInitVo;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
public interface LotteryInfoService {
    List<LotteryInitVo> initFindVos();

    List<LotteryInfo> getLotteryList();

    List<LotteryInitVo> getLotteryCodeName();

    List<LotteryInitVo> getLotteryListByGameIds(String codes);
}
