package com.easy.vo;

import lombok.Data;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Data
public class UserVo {
    private Integer id;
    private Integer currencyType;
    private String loginPwd;
    private Integer merchantId;
    private String name;
    private Integer currencyId;
    private Integer testFlag;
}
