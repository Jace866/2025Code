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
 * @Date 2025-04-12 
 */

@Data
@TableName (value = "user_wallet")
@Accessors(chain = true)
public class UserWallet implements Serializable {
	private static final long serialVersionUID =  61141835989006506L;

	/**
	 * 用户编号
	 */
   	@TableId(value = "user_id")
	private Integer userId;

	/**
	 * 当前账户余额
	 */
   	@TableField(value = "balance")
	private BigDecimal balance;

	/**
	 * 待处理金额
	 */
   	@TableField(value = "money")
	private BigDecimal money;

	/**
	 * 积分
	 */
   	@TableField(value = "integral")
	private Integer integral;

	/**
	 * USDT余额
	 */
   	@TableField(value = "usdt_balance")
	private BigDecimal usdtBalance;

}
