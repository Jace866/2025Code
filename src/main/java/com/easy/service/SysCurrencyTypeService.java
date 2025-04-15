package com.easy.service;

import com.easy.entity.SysCurrencyType;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
public interface SysCurrencyTypeService {
    List<SysCurrencyType> findList();

    SysCurrencyType findByCurrencyType(String currencyType);
}
