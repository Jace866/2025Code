package com.easy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  hello_@ 
 * @Date 2025-04-14 
 */

@Data
@TableName (value = "sys_parameter")
@Accessors(chain = true)
public class SysParameter implements Serializable {
	private static final long serialVersionUID =  9092521251587258216L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 排列顺序,按数值大小排序。数值越小，显示顺序越靠前
	 */
   	@TableField(value = "seqno")
	private Integer seqno;

	/**
	 * 排列顺序,按数值大小排序。数值越小，显示顺序越靠前(第二排序，同一参数内部排序)
	 */
   	@TableField(value = "seqno2")
	private Integer seqno2;

   	@TableField(value = "type")
	private String type;

   	@TableField(value = "sort")
	private String sort;

   	@TableField(value = "name")
	private String name;

   	@TableField(value = "code")
	private String code;

   	@TableField(value = "value")
	private String value;

	/**
	 * 英文值
	 */
   	@TableField(value = "value_en")
	private String valueEn;

	/**
	 * 泰文值
	 */
   	@TableField(value = "value_th")
	private String valueTh;

	/**
	 * 越南文值
	 */
   	@TableField(value = "value_vi")
	private String valueVi;

	/**
	 * 多国语言标志:0--不需要;1--需要
	 */
   	@TableField(value = "i18n_flag")
	private Integer i18NFlag;

   	@TableField(value = "description")
	private String description;

	/**
	 * 当前状态:0--停用;1--启用;2--隐藏
	 */
   	@TableField(value = "status")
	private Integer status;

	/**
	 * 缓存标志:0--不需要缓存;1--都需要缓存;2--前端电脑版需要,3-前端手机版需要，4--后台管理系统需要
	 */
   	@TableField(value = "cache_flag")
	private Integer cacheFlag;

	/**
	 * 操作时间
	 */
   	@TableField(value = "operate_time")
	private Date operateTime;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

}
