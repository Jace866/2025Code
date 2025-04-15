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
 * @Date 2025-04-12 
 */

@Data
@TableName (value = "user_transaction_api_log")
@Accessors(chain = true)
public class UserTransactionApiLog implements Serializable {
	private static final long serialVersionUID =  761069214082168532L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "user_id")
	private Integer userId;

   	@TableField(value = "trans_no")
	private String transNo;

   	@TableField(value = "trans_type")
	private Integer transType;

   	@TableField(value = "trans_time")
	private Date transTime;

   	@TableField(value = "trans_money")
	private BigDecimal transMoney;

   	@TableField(value = "description")
	private String description;

	/**
	 * 手机操作的记录:0-否，1-是
	 */
   	@TableField(value = "mobile_flag")
	private Integer mobileFlag;

	/**
	 * 状态 1-待处理,2-成功,3-失败
	 */
   	@TableField(value = "status")
	private Integer status;

	/**
	 * 失败记录手工检查标志:0-未检查，1-已检查
	 */
   	@TableField(value = "check_flag")
	private Integer checkFlag;

	/**
	 * 补单时间
	 */
   	@TableField(value = "operate_time")
	private Date operateTime;

   	@TableField(value = "remark")
	private String remark;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

}
