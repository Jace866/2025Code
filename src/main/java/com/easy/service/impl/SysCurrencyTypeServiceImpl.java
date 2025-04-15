package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.SysCurrencyTypeDao;

import com.easy.entity.SysCurrencyType;
import com.easy.service.SysCurrencyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class SysCurrencyTypeServiceImpl implements SysCurrencyTypeService {
    @Autowired
    private SysCurrencyTypeDao sysCurrencyTypeDao;
    @Override
    public List<SysCurrencyType> findList() {
        QueryWrapper<SysCurrencyType> queryWrapper = new QueryWrapper<>();
        return sysCurrencyTypeDao.selectList(queryWrapper);
    }

    @Override
    public SysCurrencyType findByCurrencyType(String currencyType) {
        QueryWrapper<SysCurrencyType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", currencyType);
        return sysCurrencyTypeDao.selectOne(queryWrapper);
    }
}
