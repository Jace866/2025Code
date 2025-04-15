package com.easy.vo;

import lombok.Data;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Data
public class LotteryInfoVo {
    private Integer lotteryId;
    private String lotteryCode;
    private String lotteryName;
    private String lotteryType;
    private Integer issues;
    private Integer issueType;
    private Integer sendFlag;
    private Integer status;
    private Integer merchantId;
    private String tableName;
}
