package com.easy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @Description  
 * @Author  hello_@ 
 * @Date 2025-04-14 
 */

@Data
@TableName (value = "lottery_method")
@Accessors(chain = true)
public class LotteryMethod implements Serializable {
	private static final long serialVersionUID =  2717500061960037825L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

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

	/**
	 * 报表分类代码
	 */
   	@TableField(value = "report_code")
	private String reportCode;

	/**
	 * 即时注单代码
	 */
   	@TableField(value = "instant_code")
	private String instantCode;

	/**
	 * 手机显示名称
	 */
   	@TableField(value = "mobile_name")
	private String mobileName;

	/**
	 * 报表分类名称
	 */
   	@TableField(value = "report_name")
	private String reportName;

   	@TableField(value = "adjust_id")
	private Integer adjustId;

   	@TableField(value = "comm_id")
	private Integer commId;

   	@TableField(value = "ratio_id")
	private Integer ratioId;

   	@TableField(value = "makeup_id")
	private Integer makeupId;

   	@TableField(value = "status")
	private Integer status;

}
