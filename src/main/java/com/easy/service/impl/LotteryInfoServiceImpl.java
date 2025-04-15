package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.LotteryInfoDao;
import com.easy.entity.LotteryInfo;
import com.easy.service.LotteryInfoService;
import com.easy.vo.LotteryInitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class LotteryInfoServiceImpl implements LotteryInfoService {
    @Autowired
    private LotteryInfoDao lotteryInfoDao;

    @Override
    public List<LotteryInitVo> initFindVos() {
        return lotteryInfoDao.initFindVos();
    }

    @Override
    public List<LotteryInfo> getLotteryList() {
        List<Integer> status = new ArrayList<>();
        status.add(0);
        status.add(1);
        QueryWrapper<LotteryInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("seq_no", 1);
        wrapper.in("status", status);
        wrapper.orderByAsc("show_order");
        return lotteryInfoDao.selectList(wrapper);
    }

    @Override
    public List<LotteryInitVo> getLotteryCodeName() {
        return lotteryInfoDao.getLotteryCodeName();
    }

    @Override
    public List<LotteryInitVo> getLotteryListByGameIds(String codes) {
        return lotteryInfoDao.getLotteryListByGameIds(codes);
    }
}
