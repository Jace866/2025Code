package com.easy.vo;

import lombok.Data;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Data
public class SysMerchantVo {
    private Integer id;
    private String provider;
    private String walletAipAddress;
    private Integer isTran;
}
