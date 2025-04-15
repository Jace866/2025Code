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
@TableName (value = "user_info_ext")
@Accessors(chain = true)
public class UserInfoExt implements Serializable {
	private static final long serialVersionUID =  4262958479132119695L;

	/**
	 * 用户编号
	 */
   	@TableField(value = "user_id")
	private Integer userId;

	/**
	 * 性别:0-男;1-女
	 */
   	@TableField(value = "sex")
	private Integer sex;

	/**
	 * 邮箱
	 */
   	@TableField(value = "email")
	private String email;

	/**
	 * 手机
	 */
   	@TableField(value = "phone")
	private String phone;

	/**
	 * QQ号码
	 */
   	@TableField(value = "qqnum")
	private String qqnum;

	/**
	 * 微信号
	 */
   	@TableField(value = "weixin")
	private String weixin;

   	@TableField(value = "des_key")
	private String desKey;

	/**
	 * 生日 yyyy-mm-dd
	 */
   	@TableField(value = "birthday")
	private String birthday;

	/**
	 * 真实姓名
	 */
   	@TableField(value = "real_name")
	private String realName;

	/**
	 * 累计充值总额
	 */
   	@TableField(value = "deposit_money")
	private BigDecimal depositMoney;

	/**
	 * 累计充值次数
	 */
   	@TableField(value = "deposit_count")
	private Integer depositCount;

	/**
	 * 累计充值总额(管理员入款)
	 */
   	@TableField(value = "deposit_money2")
	private BigDecimal depositMoney2;

	/**
	 * 累计充值次数(管理员入款)
	 */
   	@TableField(value = "deposit_count2")
	private Integer depositCount2;

	/**
	 * 累计返水总额(每期六合返水累计为1条记录)
	 */
   	@TableField(value = "rebate_money")
	private BigDecimal rebateMoney;

	/**
	 * 累计返水次数(每期六合返水累计为1条记录)
	 */
   	@TableField(value = "rebate_count")
	private Integer rebateCount;

	/**
	 * 累计提款总额
	 */
   	@TableField(value = "withdraw_money")
	private BigDecimal withdrawMoney;

	/**
	 * 累计提款次数
	 */
   	@TableField(value = "withdraw_count")
	private Integer withdrawCount;

	/**
	 * 累计提款总额(管理员出款)
	 */
   	@TableField(value = "withdraw_money2")
	private BigDecimal withdrawMoney2;

	/**
	 * 累计提款次数(管理员出款)
	 */
   	@TableField(value = "withdraw_count2")
	private Integer withdrawCount2;

	/**
	 * 最近一次成功充值时间
	 */
   	@TableField(value = "deposit_time")
	private Date depositTime;

	/**
	 * 最近一次成功提款时间
	 */
   	@TableField(value = "withdraw_time")
	private Date withdrawTime;

	/**
	 * 电脑版声音关闭标志:0-打开;1-关闭
	 */
   	@TableField(value = "sound_flag_pc")
	private Integer soundFlagPc;

	/**
	 * 手机版声音关闭标志:0-打开;1-关闭
	 */
   	@TableField(value = "sound_flag_mb")
	private Integer soundFlagMb;

	/**
	 * 子账号入款最大金额, 最大16777215
	 */
   	@TableField(value = "money_max")
	private Integer moneyMax;

	/**
	 * 允许子账号查看会员敏感信息的标志:0-不允许;1-允许
	 */
   	@TableField(value = "private_flag")
	private Integer privateFlag;

	/**
	 * 允许子账号迁移会员的标志:0-不允许;1-允许
	 */
   	@TableField(value = "transfer_flag")
	private Integer transferFlag;

	/**
	 * 允许子账号导出会员资料的标志:0-不允许;1-允许
	 */
   	@TableField(value = "export_flag")
	private Integer exportFlag;

	/**
	 * 允许子账号修改会员登录密码的标志:0-不允许;1-允许
	 */
   	@TableField(value = "login_flag")
	private Integer loginFlag;

	/**
	 * 允许子账号修改会员提款密码的标志:0-不允许;1-允许
	 */
   	@TableField(value = "withdraw_flag")
	private Integer withdrawFlag;

	/**
	 * 允许子账号修改会员真实姓名的标志:0-不允许,1-允许
	 */
   	@TableField(value = "realname_flag")
	private Integer realnameFlag;

	/**
	 * 允许子账号修改会员状态的标志:0-不允许,1-允许
	 */
   	@TableField(value = "status_flag")
	private Integer statusFlag;

	/**
	 * 所属角色
	 */
   	@TableField(value = "role_id")
	private Integer roleId;

	/**
	 * 是否推送投注内容
	 */
   	@TableField(value = "push_bet")
	private Integer pushBet;

	/**
	 * 被禁言标志：0-被禁言,1-可发言
	 */
   	@TableField(value = "chat_enable")
	private Integer chatEnable;

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
	 * 聊天室头像
	 */
   	@TableField(value = "head_img")
	private String headImg;

	/**
	 * 头像已修改次数
	 */
   	@TableField(value = "head_times")
	private Integer headTimes;

	/**
	 * 昵称剩余修改次数
	 */
   	@TableField(value = "times_remain")
	private Integer timesRemain;

	/**
	 * 当天已抢红包次数
	 */
   	@TableField(value = "redpack_times")
	private Integer redpackTimes;

	/**
	 * 单个会员每天允许充值次数
	 */
   	@TableField(value = "deposit_num")
	private Integer depositNum;

	/**
	 * 单个会员每天允许提款次数
	 */
   	@TableField(value = "withdraw_num")
	private Integer withdrawNum;

	/**
	 * 充值时是否允许代充,0--不允许，1--允许
	 */
   	@TableField(value = "deposit_allow")
	private Integer depositAllow;

	/**
	 * 提款时是否允许代充,0--不允许，1--允许
	 */
   	@TableField(value = "withdraw_allow")
	private Integer withdrawAllow;

	/**
	 * 后台操作员
	 */
   	@TableField(value = "operator")
	private String operator;

   	@TableField(value = "is_love")
	private String isLove;

	/**
	 * 最近玩过的游戏
	 */
   	@TableField(value = "is_recent")
	private String isRecent;

	/**
	 * userId|username DES加密值
	 */
   	@TableField(value = "chat_fk")
	private String chatFk;

	/**
	 * 最后一次密码修改时间
	 */
   	@TableField(value = "change_pwd_times")
	private Date changePwdTimes;

	/**
	 * 兴趣彩种设置
	 */
   	@TableField(value = "is_focus")
	private String isFocus;

	/**
	 * 拦截锁定状态
	 */
   	@TableField(value = "intercept_status")
	private Integer interceptStatus;

	/**
	 * 是否允许抢红包:0-不允许,1-允许
	 */
   	@TableField(value = "red_packet")
	private Integer redPacket;

	/**
	 * 是否允许晒单:0-不允许,1-允许
	 */
   	@TableField(value = "sun_drying")
	private Integer sunDrying;

	/**
	 * 是否允许修改头像:0-不允许,1-允许
	 */
   	@TableField(value = "avatar")
	private Integer avatar;

	/**
	 * 是否允许打赏:0-不允许,1-允许
	 */
   	@TableField(value = "reward")
	private Integer reward;

	/**
	 * 开启进游戏自动转入全部金额
	 */
   	@TableField(value = "tran_allbal")
	private Integer tranAllbal;

	/**
	 * 解锁和解冻需要会员修改密码
	 */
   	@TableField(value = "ischange_password")
	private Integer ischangePassword;

	/**
	 * 记录登录次数自动锁定
	 */
   	@TableField(value = "login_count")
	private Integer loginCount;

	/**
	 * 用户设备
	 */
   	@TableField(value = "device")
	private String device;

	/**
	 * 棋牌APP VIP级别
	 */
   	@TableField(value = "vip_level")
	private Integer vipLevel;

	/**
	 * 棋牌累计有效投注金额，用于确定会员VIP级别
	 */
   	@TableField(value = "total_bet")
	private Integer totalBet;

	/**
	 * 记录棋牌APP用户推广上下级关系
	 */
   	@TableField(value = "regfromid2")
	private String regfromid2;

	/**
	 * 棋牌APP注册方式: 0--非棋牌注册, 1-上级直接开户, 2-网络注册
	 */
   	@TableField(value = "reg_type")
	private Integer regType;

	/**
	 * 直接下级会员数量
	 */
   	@TableField(value = "children")
	private Integer children;

   	@TableField(value = "parent_id")
	private Integer parentId;

	/**
	 * 体育会员代理域名
	 */
   	@TableField(value = "ag_domain")
	private String agDomain;

	/**
	 * 会员代理申请状态：0-默认,1-提交申请,2-审核通过
	 */
   	@TableField(value = "ag_status")
	private Integer agStatus;

   	@TableField(value = "ag_reg_time")
	private Date agRegTime;

}
