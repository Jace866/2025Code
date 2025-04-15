package com.easy.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LotteryInitVo {
    private Integer lotteryId;
    private String lotteryCode;
    private String lotteryName;
    private Integer showOrder;
}
