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
@TableName (value = "user_transaction_api")
@Accessors(chain = true)
public class UserTransactionApi implements Serializable {
	private static final long serialVersionUID =  3099840228105754472L;

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
	 * 游戏序号:0-沙巴,1-代理赔率差,2-AG,3-开元棋牌,4-,5-棋牌游戏,取彩种Id个位数
	 */
   	@TableField(value = "api_idx")
	private Integer apiIdx;

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
