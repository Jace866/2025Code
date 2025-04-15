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
 * @Date 2025-04-15 
 */

@Data
@TableName (value = "user_online")
@Accessors(chain = true)
public class UserOnline implements Serializable {
	private static final long serialVersionUID =  1119958864552545109L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "server_id")
	private Integer serverId;

   	@TableField(value = "session_id")
	private String sessionId;

	/**
	 * 登录日志ID
	 */
   	@TableField(value = "log_id")
	private Integer logId;

	/**
	 * 用户编号
	 */
   	@TableField(value = "user_id")
	private Integer userId;

	/**
	 * 用户名称
	 */
   	@TableField(value = "name")
	private String name;

   	@TableField(value = "login_time")
	private Date loginTime;

	/**
	 * 最新活动时间，记录获取最新公告时间
	 */
   	@TableField(value = "action_time")
	private Date actionTime;

   	@TableField(value = "login_ip")
	private String loginIp;

	/**
	 * 登录地址
	 */
   	@TableField(value = "login_address")
	private String loginAddress;

   	@TableField(value = "login_domain")
	private String loginDomain;

	/**
	 * 用户类别:0--正式,1-测试，;2--试玩
	 */
   	@TableField(value = "try_flag")
	private Integer tryFlag;

	/**
	 * 登录类别:1--电脑版登录;2--手机版登录
	 */
   	@TableField(value = "mobile_flag")
	private Integer mobileFlag;

	/**
	 * 踢出系统标志:0--否,1-是
	 */
   	@TableField(value = "kickout_flag")
	private Integer kickoutFlag;

	/**
	 * 聊天室token
	 */
   	@TableField(value = "token")
	private String token;

	/**
	 * 进入聊天室标志:0--未进入,1-已进入
	 */
   	@TableField(value = "chat_flag")
	private Integer chatFlag;

	/**
	 * 登录随机数
	 */
   	@TableField(value = "random_num")
	private String randomNum;

   	@TableField(value = "status")
	private Integer status;

	/**
	 * 商户ID
	 */
   	@TableField(value = "merchant_id")
	private Integer merchantId;

}
