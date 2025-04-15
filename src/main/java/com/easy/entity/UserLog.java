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
@TableName (value = "user_log")
@Accessors(chain = true)
public class UserLog implements Serializable {
	private static final long serialVersionUID =  8027340452510487687L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 登录时服务器Id
	 */
   	@TableField(value = "server_id_in")
	private Integer serverIdIn;

	/**
	 * 退出时服务器Id
	 */
   	@TableField(value = "server_id_out")
	private Integer serverIdOut;

	/**
	 * 用户编号
	 */
   	@TableField(value = "user_id")
	private Integer userId;

	/**
	 * 试玩用户:0--否;2--是
	 */
   	@TableField(value = "try_flag")
	private Integer tryFlag;

	/**
	 * 登录类别:1--电脑版登录;2--手机版登录
	 */
   	@TableField(value = "mobile_flag")
	private Integer mobileFlag;

	/**
	 * 登录IP
	 */
   	@TableField(value = "login_ip")
	private String loginIp;

	/**
	 * 登录地址
	 */
   	@TableField(value = "login_address")
	private String loginAddress;

	/**
	 * 登录时间
	 */
   	@TableField(value = "login_time")
	private Date loginTime;

	/**
	 * 登出时间
	 */
   	@TableField(value = "logout_time")
	private Date logoutTime;

	/**
	 * 登录域名
	 */
   	@TableField(value = "login_domain")
	private String loginDomain;

	/**
	 * 会话token
	 */
   	@TableField(value = "token")
	private String token;

	/**
	 * 备注
	 */
   	@TableField(value = "remark")
	private String remark;

	/**
	 * 登录设备信息
	 */
   	@TableField(value = "device")
	private String device;

}
