package com.easy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description  
 * @Author  hello_@ 
 * @Date 2025-04-14 
 */

@Data
@Accessors(chain = true)
public class UserPlayVo {

	private Integer id;

	private Integer userId;

	private String currencyType;

	private String name;

	private String playNo;

	private String playTime;

	private Integer lotteryId;

	private String issue;

	private String ptype;

	private Integer itemId;

	private String itemCode;

	private String content;

	private Integer num;

	private BigDecimal money;

	private BigDecimal validBet;

	private BigDecimal ratio;

	private BigDecimal ratio2;

	private BigDecimal bonus;

	private BigDecimal wins;

	private BigDecimal pct;

	private BigDecimal comm;

	private String openCode;

	private String openTime;

	private Integer status;

	private Integer testFlag;

	private Integer mobileFlag;

	private Integer isCallbet;

	private String transactionId;

	private String remark;

}
