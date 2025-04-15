package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.SysMerchantDao;
import com.easy.entity.SysMerchant;
import com.easy.service.SysMerchantService;
import com.easy.vo.SysMerchantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class SysMerchantServiceImpl implements SysMerchantService {

    @Autowired
    private SysMerchantDao sysMerchantDao;

    @Override
    public List<SysMerchantVo> findList() {
        return sysMerchantDao.findList();
    }

    @Override
    public SysMerchant findByStatusAndProvider(int status, String provider) {
        return sysMerchantDao.findByStatusAndProvider(status, provider);
    }

    @Override
    public SysMerchant findByMerchantIdAndStatus(int status, int merchantId) {
        return sysMerchantDao.findByMerchantIdAndStatus(status, merchantId);
    }

    @Override
    public SysMerchant queryByMerchantDomain(String domain) {
        QueryWrapper<SysMerchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("domain", "%" + domain + "%");
        return sysMerchantDao.selectOne(queryWrapper);
    }

    @Override
    public SysMerchant queryByMerchantDomainAndIp(String pid, String domain, String ip) {
        return sysMerchantDao.queryByMerchantDomainAndIp(pid, domain, ip);
    }

    @Override
    public SysMerchant queryByMerchantDomain(String pid, String domain, String ip) {
        return sysMerchantDao.queryByMerchantDomain(pid, domain, ip);
    }

    @Override
    public BigDecimal queryBalance(int merchantId) {
        return sysMerchantDao.queryBalance(merchantId);
    }

    @Override
    public void updateBalanceById(double merchantBalance, int merchantId) {
        sysMerchantDao.updateBalanceById(merchantBalance, merchantId);
    }
}
