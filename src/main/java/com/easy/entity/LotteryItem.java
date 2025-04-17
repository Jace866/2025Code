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
@TableName (value = "lottery_item")
@Accessors(chain = true)
public class LotteryItem implements Serializable {
	private static final long serialVersionUID =  3773001318873163359L;

   	@TableId(value = "item_id")
	private Integer itemId;

   	@TableField(value = "lottery_type")
	private String lotteryType;

	/**
	 * 投注项代码
	 */
   	@TableField(value = "item_code")
	private String itemCode;

   	@TableField(value = "item_name")
	private String itemName;

   	@TableField(value = "name_en")
	private String nameEn;

   	@TableField(value = "name_th")
	private String nameTh;

   	@TableField(value = "name_vi")
	private String nameVi;

}
