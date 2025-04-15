package com.easy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description  
 * @Author  hello_@ 
 * @Date 2025-04-14 
 */

@Data
@TableName (value = "lottery_ratio_instant")
@Accessors(chain = true)
public class LotteryRatioInstant implements Serializable {
	private static final long serialVersionUID =  2774655207328955413L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "issue")
	private String issue;

   	@TableField(value = "lottery_id")
	private Integer lotteryId;

   	@TableField(value = "method_id")
	private Integer methodId;

   	@TableField(value = "method_name")
	private String methodName;

   	@TableField(value = "sub_id")
	private Integer subId;

   	@TableField(value = "sub_name")
	private String subName;

   	@TableField(value = "item_id")
	private Integer itemId;

	/**
	 * 投注项代码
	 */
   	@TableField(value = "item_code")
	private String itemCode;

   	@TableField(value = "item_name")
	private String itemName;

   	@TableField(value = "makeup_id")
	private Integer makeupId;

   	@TableField(value = "adjust_id")
	private Integer adjustId;

   	@TableField(value = "adjust2_id")
	private Integer adjust2Id;

   	@TableField(value = "comm_id")
	private Integer commId;

   	@TableField(value = "ratio_id")
	private Integer ratioId;

   	@TableField(value = "ratio")
	private BigDecimal ratio;

   	@TableField(value = "sub_ratio")
	private BigDecimal subRatio;

   	@TableField(value = "min_ratio")
	private BigDecimal minRatio;

   	@TableField(value = "max_ratio")
	private BigDecimal maxRatio;

   	@TableField(value = "play_money")
	private BigDecimal playMoney;

   	@TableField(value = "adjust_money")
	private BigDecimal adjustMoney;

   	@TableField(value = "status")
	private Integer status;

   	@TableField(value = "adjust_quota")
	private Integer adjustQuota;

   	@TableField(value = "is_show")
	private Integer isShow;

   	@TableField(value = "code_name")
	private String codeName;

   	@TableField(value = "mobile_name")
	private String mobileName;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

}
