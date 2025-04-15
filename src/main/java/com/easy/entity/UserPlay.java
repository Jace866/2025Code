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
@TableName (value = "user_play")
@Accessors(chain = true)
public class UserPlay implements Serializable {
	private static final long serialVersionUID =  6704996028876587117L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "user_id")
	private Integer userId;

	/**
	 * 币种
	 */
   	@TableField(value = "currency_type")
	private String currencyType;

   	@TableField(value = "regfrom")
	private String regfrom;

	/**
	 * API业务订单号
	 */
   	@TableField(value = "ref_no")
	private String refNo;

   	@TableField(value = "play_no")
	private String playNo;

   	@TableField(value = "play_time")
	private Date playTime;

   	@TableField(value = "lottery_id")
	private Integer lotteryId;

   	@TableField(value = "issue")
	private String issue;

   	@TableField(value = "ptype")
	private String ptype;

   	@TableField(value = "item_id")
	private Integer itemId;

   	@TableField(value = "item_code")
	private String itemCode;

   	@TableField(value = "content")
	private String content;

	/**
	 * 注数
	 */
   	@TableField(value = "num")
	private Integer num;

   	@TableField(value = "money")
	private BigDecimal money;

	/**
	 * 第三方游戏有效投注金额
	 */
   	@TableField(value = "valid_bet")
	private BigDecimal validBet;

   	@TableField(value = "ratio")
	private BigDecimal ratio;

   	@TableField(value = "ratio2")
	private BigDecimal ratio2;

   	@TableField(value = "bonus")
	private BigDecimal bonus;

	/**
	 * 第三方游戏的下注结果(输赢金额)
	 */
   	@TableField(value = "wins")
	private BigDecimal wins;

	/**
	 * 退水比例(%)
	 */
   	@TableField(value = "pct")
	private BigDecimal pct;

	/**
	 * 退水金额
	 */
   	@TableField(value = "comm")
	private BigDecimal comm;

   	@TableField(value = "open_code")
	private String openCode;

	/**
	 * 开奖，结算时间，用于报表统计
	 */
   	@TableField(value = "open_time")
	private Date openTime;

	/**
	 * 记录状态:1-等待开奖，2-已中奖，3-未中奖
	 */
   	@TableField(value = "status")
	private Integer status;

	/**
	 * 测试用户投注的记录:0-否，1-是
	 */
   	@TableField(value = "test_flag")
	private Integer testFlag;

	/**
	 * 手机投注的记录:0-否，1-是
	 */
   	@TableField(value = "mobile_flag")
	private Integer mobileFlag;

	/**
	 * 是否为跟注的记录:0-否，1-是
	 */
   	@TableField(value = "is_callbet")
	private Integer isCallbet;

	/**
	 * 关联汇总扣款订单
	 */
   	@TableField(value = "transaction_id")
	private String transactionId;

	/**
	 * 已派奖标志:0-否，1-是
	 */
   	@TableField(value = "payed_flag")
	private Integer payedFlag;

	/**
	 * 判断是否已发送
	 */
   	@TableField(value = "is_send")
	private Integer isSend;

	/**
	 * 判断投注注单是否已发送
	 */
   	@TableField(value = "is_bet_send")
	private Integer isBetSend;

	/**
	 * 登录随机数
	 */
   	@TableField(value = "random_num")
	private String randomNum;

	/**
	 * 投注IP
	 */
   	@TableField(value = "bet_ip")
	private String betIp;

   	@TableField(value = "remark")
	private String remark;

}
