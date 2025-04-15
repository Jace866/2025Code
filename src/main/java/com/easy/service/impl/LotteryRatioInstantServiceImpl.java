package com.easy.service.impl;

import com.easy.dao.LotteryRatioInstantDao;
import com.easy.service.LotteryRatioInstantService;
import com.easy.vo.LotteryRatioInstantInitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class LotteryRatioInstantServiceImpl implements LotteryRatioInstantService {
    @Autowired
    private LotteryRatioInstantDao lotteryRatioInstantDao;

    @Override
    public List<LotteryRatioInstantInitVo> findVos() {
        return lotteryRatioInstantDao.findVos();
    }
}
