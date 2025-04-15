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
@TableName (value = "sys_merchant")
@Accessors(chain = true)
public class SysMerchant implements Serializable {
	private static final long serialVersionUID =  4926864028764439474L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 代理ID
	 */
   	@TableField(value = "user_id")
	private Integer userId;

	/**
	 * 代理账号
	 */
   	@TableField(value = "agent")
	private String agent;

	/**
	 * 商户ID
	 */
   	@TableField(value = "provider")
	private String provider;

	/**
	 * 当前账户余额
	 */
   	@TableField(value = "balance")
	private BigDecimal balance;

	/**
	 * 商户名称
	 */
   	@TableField(value = "name")
	private String name;

	/**
	 * 前缀名称
	 */
   	@TableField(value = "prefix_name")
	private String prefixName;

	/**
	 * 商户logo
	 */
   	@TableField(value = "logo")
	private String logo;

	/**
	 * DES密钥
	 */
   	@TableField(value = "des_key")
	private String desKey;

	/**
	 * MD5密钥
	 */
   	@TableField(value = "md5_key")
	private String md5Key;

	/**
	 * 游戏
	 */
   	@TableField(value = "game")
	private String game;

	/**
	 * 游戏ID
	 */
   	@TableField(value = "game_id")
	private String gameId;

	/**
	 * 单一钱包API接口地址
	 */
   	@TableField(value = "wallet_aip_address")
	private String walletAipAddress;

	/**
	 * 是否支持金额转换0-关闭,1-开户
	 */
   	@TableField(value = "tran_flag")
	private Integer tranFlag;

	/**
	 * 平台类型
	 */
   	@TableField(value = "api_type")
	private Integer apiType;

	/**
	 * 是否开启聊天室，0-关闭，1-开启
	 */
   	@TableField(value = "chat")
	private Integer chat;

	/**
	 * 是否开启查询余额，0-关闭, 1-开户, 默认为0
	 */
   	@TableField(value = "is_bal")
	private Integer isBal;

	/**
	 * 是否开启额度转换，0-关闭, 1-开户, 默认为0
	 */
   	@TableField(value = "is_tran")
	private Integer isTran;

	/**
	 * 是否开启固定币种，0-关闭, 1-开户, 默认为0
	 */
   	@TableField(value = "is_currency")
	private Integer isCurrency;

	/**
	 * 验证类型
	 */
   	@TableField(value = "is_ipwhitelist")
	private Integer isIpwhitelist;

	/**
	 * ip白名单
	 */
   	@TableField(value = "ip_whitelist")
	private String ipWhitelist;

	/**
	 * 域名绑定
	 */
   	@TableField(value = "domain")
	private String domain;

	/**
	 * 公告
	 */
   	@TableField(value = "notice")
	private String notice;

	/**
	 * 状态，0-关闭，1-正常，2-测试
	 */
   	@TableField(value = "status")
	private Integer status;

	/**
	 * 系统维护时间
	 */
   	@TableField(value = "maintenance_time")
	private String maintenanceTime;

	/**
	 * 创建时间
	 */
   	@TableField(value = "create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
   	@TableField(value = "update_date")
	private Date updateDate;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

}
