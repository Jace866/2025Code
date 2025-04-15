package com.easy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * @Description  
 * @Author  hello_@ 
 * @Date 2025-04-14 
 */

@Data
@TableName (value = "user_transaction")
@Accessors(chain = true)
public class UserTransaction implements Serializable {
	private static final long serialVersionUID =  2897472857625005316L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "user_id")
	private Integer userId;

	/**
	 * 币种
	 */
   	@TableField(value = "currency_type")
	private String currencyType;

	/**
	 * 参照编号:如注单编号，充值编号
	 */
   	@TableField(value = "ref_no")
	private String refNo;

   	@TableField(value = "trans_no")
	private String transNo;

   	@TableField(value = "trans_type")
	private Integer transType;

   	@TableField(value = "trans_time")
	private Date transTime;

   	@TableField(value = "trans_money")
	private BigDecimal transMoney;

   	@TableField(value = "before_money")
	private BigDecimal beforeMoney;

   	@TableField(value = "after_money")
	private BigDecimal afterMoney;

	/**
	 * 单一钱包API转换类型
下注 BET
派彩 WIN
打和退款 REFUND
结算返水 CASHOUT
領红包加款 CANCEL_DEDUCT
撤单 CANCEL_RETURN
签到加款 SETTLEMENT_ROLLBACK_RETURN
余额宝转入转出 SETTLEMENT_ROLLBACK_DEDUCT
	 */
   	@TableField(value = "trans_type_api")
	private String transTypeApi;

	/**
	 * 用于结算派奖加款成功再显示
	 */
   	@TableField(value = "take_flag")
	private Integer takeFlag;

   	@TableField(value = "remark")
	private String remark;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

}
