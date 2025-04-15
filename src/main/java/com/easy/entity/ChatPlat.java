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
 * @Date 2025-04-14 
 */

@Data
@TableName (value = "chat_plat")
@Accessors(chain = true)
public class ChatPlat implements Serializable {
	private static final long serialVersionUID =  2147259460449500029L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 平台编码
	 */
   	@TableField(value = "plat_code")
	private String platCode;

	/**
	 * 平台名称
	 */
   	@TableField(value = "plat_name")
	private String platName;

	/**
	 * 后台域名
	 */
   	@TableField(value = "plat_url")
	private String platUrl;

	/**
	 * 加密私钥
	 */
   	@TableField(value = "plat_key")
	private String platKey;

	/**
	 * 聊天室地址
	 */
   	@TableField(value = "chat_url")
	private String chatUrl;

	/**
	 * 是否使用动态聊天室地址，1-生产环境；0-开发环境
	 */
   	@TableField(value = "is_dyn_url")
	private Integer isDynUrl;

	/**
	 * 使用IP访问手机版时，代理服务器设置的端口
	 */
   	@TableField(value = "port")
	private Integer port;

	/**
	 * 最低下注额度
	 */
   	@TableField(value = "minbet_money")
	private Integer minbetMoney;

	/**
	 * 是否自动:0-不自动打开, 1-自动打开（适合用户少的平台）
	 */
   	@TableField(value = "auto_connect")
	private Integer autoConnect;

	/**
	 * 聊天室状态:0-关闭, 1-关闭, 2-内测
	 */
   	@TableField(value = "chat_open")
	private Integer chatOpen;

	/**
	 * 计划发布方式:1-手动发布, 2-软件发布
	 */
   	@TableField(value = "send_plan")
	private Integer sendPlan;

	/**
	 * 计划推送游戏
	 */
   	@TableField(value = "plan_game")
	private String planGame;

	/**
	 * 计划底部信息
	 */
   	@TableField(value = "plan_footer")
	private String planFooter;

	/**
	 * 发言时段(开始)
	 */
   	@TableField(value = "begin_time")
	private String beginTime;

	/**
	 * 发言时段(结束)
	 */
   	@TableField(value = "end_time")
	private String endTime;

	/**
	 * 聊天室默认皮肤颜色:blue-蓝, red-红, gold-金黄色
	 */
   	@TableField(value = "skin_color")
	private String skinColor;

	/**
	 * 历史消息最大长度
	 */
   	@TableField(value = "history_length")
	private Integer historyLength;

	/**
	 * 聊天消息保留天数，默认2天
	 */
   	@TableField(value = "msg_days")
	private Integer msgDays;

	/**
	 * 不受充值量，打码量限制，可以随时发言的帐号
	 */
   	@TableField(value = "unlimited_account")
	private String unlimitedAccount;

	/**
	 * IP黑名单
	 */
   	@TableField(value = "ip_blacks")
	private String ipBlacks;

	/**
	 * 当日发红包上限
	 */
   	@TableField(value = "packet_day_count")
	private Integer packetDayCount;

	/**
	 * 单个红包金额范围(下限)
	 */
   	@TableField(value = "packet_min_money")
	private BigDecimal packetMinMoney;

	/**
	 * 单个红包金额范围(上限)
	 */
   	@TableField(value = "packet_max_money")
	private BigDecimal packetMaxMoney;

	/**
	 * 单个红包人数范围(下限)
	 */
   	@TableField(value = "packet_min_people")
	private Integer packetMinPeople;

	/**
	 * 单个红包人数范围(上限)
	 */
   	@TableField(value = "packet_max_people")
	private Integer packetMaxPeople;

	/**
	 * 红包附言
	 */
   	@TableField(value = "packet_remark")
	private String packetRemark;

	/**
	 * 红包防挂策略
	 */
   	@TableField(value = "packet_strategy")
	private Integer packetStrategy;

	/**
	 * 防挂操作:30m--禁言30分钟, 1h--禁言1小时, 6h--禁言6小时, 1d--禁言1天, 2d--禁言2天, 3d--禁言3天, strategy1--永久禁言, strategy2--永久封停, strategy3--永久封停并拉黑
	 */
   	@TableField(value = "strategy_operation")
	private String strategyOperation;

	/**
	 * 是否允许跟单：0-不允许, 1-允许
	 */
   	@TableField(value = "follow_bet")
	private Integer followBet;

	/**
	 * 9G体育是否允许跟单：0-不允许, 1-允许
	 */
   	@TableField(value = "sport_bet")
	private Integer sportBet;

	/**
	 * 是否显示推送测试帐号的投注：0-不显示, 1-显示
	 */
   	@TableField(value = "test_push")
	private Integer testPush;

	/**
	 * 创建人
	 */
   	@TableField(value = "create_by")
	private String createBy;

	/**
	 * 创建时间
	 */
   	@TableField(value = "create_date")
	private Date createDate;

	/**
	 * 修改人
	 */
   	@TableField(value = "update_by")
	private String updateBy;

	/**
	 * 修改时间
	 */
   	@TableField(value = "update_date")
	private Date updateDate;

}
