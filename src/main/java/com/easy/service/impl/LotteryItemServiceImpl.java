package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.LotteryItemDao;
import com.easy.entity.LotteryItem;
import com.easy.service.LotteryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class LotteryItemServiceImpl implements LotteryItemService {
    @Autowired
    private LotteryItemDao lotteryItemDao;
    @Override
    public String queryItemName(String lotteryType, String itemCode) {
        QueryWrapper<LotteryItem> wrapper = new QueryWrapper<>();
        wrapper.eq("lottery_type", lotteryType);
        wrapper.eq("item_code", itemCode);
        return lotteryItemDao.selectOne(wrapper).getItemName();
    }
}
