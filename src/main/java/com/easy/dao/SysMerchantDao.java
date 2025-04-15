package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.SysMerchant;
import com.easy.vo.SysMerchantVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Mapper
public interface SysMerchantDao extends BaseMapper<SysMerchant> {

    List<SysMerchantVo> findList();

    SysMerchant findByStatusAndProvider(@Param("status") int status,@Param("provider") String provider);

    SysMerchant findByMerchantIdAndStatus(@Param("status") int status,@Param("merchantId") int merchantId);

    SysMerchant queryByMerchantDomainAndIp(@Param("pid") String pid, @Param("domain") String domain, @Param("ip") String ip);

    SysMerchant queryByMerchantDomain(@Param("pid") String pid, @Param("domain") String domain, @Param("ip")String ip);

    BigDecimal queryBalance(@Param("pid")int merchantId);

    void updateBalanceById(@Param("merchantBalance")double merchantBalance,@Param("merchantId") int merchantId);
}
