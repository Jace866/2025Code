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
@TableName (value = "user_info")
@Accessors(chain = true)
public class User implements Serializable {
	private static final long serialVersionUID =  5354045029358887641L;

	/**
	 * 用户编号
	 */
   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 上级用户编号
	 */
   	@TableField(value = "super_id")
	private Integer superId;

	/**
	 * 用户级别
	 */
   	@TableField(value = "grade")
	private Integer grade;

	/**
	 * 用户类型
	 */
   	@TableField(value = "type")
	private Integer type;

	/**
	 * 币种，默认为CNY人民币
	 */
   	@TableField(value = "currency_id")
	private Integer currencyId;

	/**
	 * 用户名称
	 */
   	@TableField(value = "name")
	private String name;

	/**
	 * 用户昵称
	 */
   	@TableField(value = "nickname")
	private String nickname;

	/**
	 * 登录密码
	 */
   	@TableField(value = "login_pwd")
	private String loginPwd;

	/**
	 * 资金密码
	 */
   	@TableField(value = "money_pwd")
	private String moneyPwd;

   	@TableField(value = "regfrom")
	private String regfrom;

   	@TableField(value = "regfromid")
	private String regfromid;

   	@TableField(value = "ptype")
	private String ptype;

	/**
	 * 会员层次
	 */
   	@TableField(value = "member_layer")
	private String memberLayer;

	/**
	 * 资金密码
	 */
   	@TableField(value = "freeze_money")
	private BigDecimal freezeMoney;

	/**
	 * 未结算金额(投注金额)
	 */
   	@TableField(value = "bet_money")
	private BigDecimal betMoney;

	/**
	 * 最低投注限额
	 */
   	@TableField(value = "low_limit")
	private Integer lowLimit;

	/**
	 * 提示声音标志:0--关;1--开
	 */
   	@TableField(value = "audio_flag")
	private Integer audioFlag;

	/**
	 * 黑名单标志:0--否;1--是
	 */
   	@TableField(value = "black_flag")
	private Integer blackFlag;

	/**
	 * 账号类别:0--正式账号;1--测试账号
	 */
   	@TableField(value = "test_flag")
	private Integer testFlag;

	/**
	 * 账号类别:1--电脑版注册;2--手机版注册
	 */
   	@TableField(value = "reg_flag")
	private Integer regFlag;

	/**
	 * 注册域名
	 */
   	@TableField(value = "reg_domain_pc")
	private String regDomainPc;

	/**
	 * 注册域名
	 */
   	@TableField(value = "reg_domain_mb")
	private String regDomainMb;

	/**
	 * 注册连接
	 */
   	@TableField(value = "reg_link")
	private String regLink;

	/**
	 * 注册时间
	 */
   	@TableField(value = "reg_time")
	private Date regTime;

	/**
	 * 注册IP
	 */
   	@TableField(value = "reg_ip")
	private String regIp;

	/**
	 * 最近登录时间
	 */
   	@TableField(value = "login_time")
	private Date loginTime;

	/**
	 * 最近登录IP
	 */
   	@TableField(value = "login_ip")
	private String loginIp;

	/**
	 * 登录次数
	 */
   	@TableField(value = "login_num")
	private Integer loginNum;

	/**
	 * 最近登录错误时间
	 */
   	@TableField(value = "login_error_time")
	private Date loginErrorTime;

	/**
	 * 当前状态
	 */
   	@TableField(value = "status")
	private Integer status;

	/**
	 * 子账号模块权限
	 */
   	@TableField(value = "popedoms")
	private String popedoms;

	/**
	 * 所属平台，用于控制登录
	 */
   	@TableField(value = "platform")
	private String platform;

	/**
	 * 备注信息
	 */
   	@TableField(value = "remark")
	private String remark;

	/**
	 * 安全问题。问题Id问题答案之间用##号分隔
	 */
   	@TableField(value = "safe_question")
	private String safeQuestion;

	/**
	 * 启用谷歌动态密码标志:0--不启用;1--启用
	 */
   	@TableField(value = "totp_flag")
	private Integer totpFlag;

	/**
	 * TOTP密码的密钥
	 */
   	@TableField(value = "totp_key")
	private String totpKey;

	/**
	 * TOTP密码(Time-based One-time Password)二维码链接
	 */
   	@TableField(value = "totp_qr")
	private String totpQr;

	/**
	 * 修改时间
	 */
   	@TableField(value = "operate_time")
	private Date operateTime;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

	/**
	 * AG游戏下注盘口
	 */
   	@TableField(value = "ag_oddtype")
	private String agOddtype;

	/**
	 * API第三方用户公共账号
	 */
   	@TableField(value = "api_common")
	private String apiCommon;

	/**
	 * API第三方用户账号，未注册时为空。根据彩种最后一位排序,0-沙巴体育,1-代理赔率差,2-AG游戏,3-开元棋牌,4-乐游棋牌,5-棋牌游戏,...
	 */
   	@TableField(value = "api_name")
	private String apiName;

	/**
	 * API第三方用户密码，默认为空
	 */
   	@TableField(value = "api_password")
	private String apiPassword;

	/**
	 * API第三方用户状态:0-未注册,1-可正常进入游戏,2-禁止进入游戏,3-根据需要各游戏自己定义
	 */
   	@TableField(value = "api_status")
	private String apiStatus;

	/**
	 * API第三方账户余额
	 */
   	@TableField(value = "api_balance")
	private String apiBalance;

	/**
	 * 聊天室：最近2天充值量
	 */
   	@TableField(value = "chat_rech_money")
	private BigDecimal chatRechMoney;

	/**
	 * 聊天室：最近2天打码量
	 */
   	@TableField(value = "chat_bet_money")
	private Integer chatBetMoney;

	/**
	 * API商户ID
	 */
   	@TableField(value = "merchant_id")
	private Integer merchantId;

}
