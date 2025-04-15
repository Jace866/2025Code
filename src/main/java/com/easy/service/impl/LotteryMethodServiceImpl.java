package com.easy.service.impl;

import com.easy.dao.LotteryMethodDao;
import com.easy.service.LotteryMethodService;
import com.easy.vo.LotteryMethodInitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class LotteryMethodServiceImpl implements LotteryMethodService {
    @Autowired
    private LotteryMethodDao lotteryMethodDao;

    @Override
    public List<LotteryMethodInitVo> findListVo() {
        return lotteryMethodDao.findListVo();
    }
}
