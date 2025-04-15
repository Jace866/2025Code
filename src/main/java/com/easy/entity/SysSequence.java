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
@TableName (value = "sys_sequence")
@Accessors(chain = true)
public class SysSequence implements Serializable {
	private static final long serialVersionUID =  7882658796752108652L;

   	@TableField(value = "seq_name")
	private String seqName;

   	@TableField(value = "prev_value")
	private Integer prevValue;

   	@TableField(value = "post_value")
	private Integer postValue;

	/**
	 * 记录生成时间，方便数据清理
	 */
   	@TableField(value = "create_time")
	private Date createTime;

}
