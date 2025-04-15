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
 * @Date 2025-04-15 
 */

@Data
@TableName (value = "test_user")
@Accessors(chain = true)
public class TestUser implements Serializable {
	private static final long serialVersionUID =  8985298274675294082L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "session_id")
	private String sessionId;

   	@TableField(value = "balance")
	private BigDecimal balance;

   	@TableField(value = "bet_money")
	private BigDecimal betMoney;

	/**
	 * 聊天室等级
	 */
   	@TableField(value = "chat_level")
	private Integer chatLevel;

	/**
	 * 聊天室昵称
	 */
   	@TableField(value = "chat_name")
	private String chatName;

	/**
	 * 昵称剩余修改次数
	 */
   	@TableField(value = "times_remain")
	private Integer timesRemain;

   	@TableField(value = "login_ip")
	private String loginIp;

   	@TableField(value = "login_time")
	private Date loginTime;

   	@TableField(value = "logout_time")
	private Date logoutTime;

}
