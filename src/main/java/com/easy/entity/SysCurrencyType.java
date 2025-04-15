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
@TableName (value = "sys_currency_type")
@Accessors(chain = true)
public class SysCurrencyType implements Serializable {
	private static final long serialVersionUID =  7146659159569609214L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 币种类型
	 */
   	@TableField(value = "type")
	private String type;

	/**
	 * 币种名称
	 */
   	@TableField(value = "name")
	private String name;

	/**
	 * 币种汇率
	 */
   	@TableField(value = "rate")
	private BigDecimal rate;

	/**
	 * 添加/修改时间
	 */
   	@TableField(value = "operate_time")
	private Date operateTime;

	/**
	 * 修改人帐号
	 */
   	@TableField(value = "operator")
	private String operator;

}
