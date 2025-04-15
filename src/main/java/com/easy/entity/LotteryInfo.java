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
@TableName (value = "lottery_info")
@Accessors(chain = true)
public class LotteryInfo implements Serializable {
	private static final long serialVersionUID =  4467251469585028608L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

   	@TableField(value = "lottery_id")
	private Integer lotteryId;

   	@TableField(value = "lottery_code")
	private String lotteryCode;

   	@TableField(value = "lottery_name")
	private String lotteryName;

   	@TableField(value = "lottery_type")
	private String lotteryType;

	/**
	 * 彩种类别: 1-国内;2-国外;3-香港
	 */
   	@TableField(value = "lottery_attr")
	private Integer lotteryAttr;

	/**
	 * 设置热门: 0-正常;1-新彩种;2-热门彩种
	 */
   	@TableField(value = "show_top")
	private Integer showTop;

   	@TableField(value = "table_name")
	private String tableName;

   	@TableField(value = "show_order")
	private Integer showOrder;

   	@TableField(value = "seq_no")
	private Integer seqNo;

   	@TableField(value = "seq_name")
	private String seqName;

   	@TableField(value = "open_time")
	private String openTime;

   	@TableField(value = "close_time")
	private String closeTime;

   	@TableField(value = "interval_time")
	private Integer intervalTime;

   	@TableField(value = "start_issue")
	private Integer startIssue;

   	@TableField(value = "issues")
	private Integer issues;

   	@TableField(value = "delay_time")
	private Integer delayTime;

   	@TableField(value = "status")
	private Integer status;

	/**
	 * 期号格式类型: 1-yyyymmddnnn, 2-yyyynnn, 3-nnnnnnnn
	 */
   	@TableField(value = "issue_type")
	private Integer issueType;

   	@TableField(value = "send_flag")
	private Integer sendFlag;

	/**
	 * 每天自动生成下一天期号标志:0-否, 1-是
	 */
   	@TableField(value = "gen_flag")
	private Integer genFlag;

	/**
	 * 会员层次,会员分类
	 */
   	@TableField(value = "member_layer")
	private String memberLayer;

	/**
	 * 是否有免费试玩
	 */
   	@TableField(value = "free_play")
	private Integer freePlay;

	/**
	 * 维护时间，仅对API类型的彩种有效
	 */
   	@TableField(value = "maintenance_time")
	private String maintenanceTime;

	/**
	 * 告知触发器那个系统修改的:1,2-后台系统，3,4--代理系统，5,6--电脑版系统，7,8--手机版系统,9,10--总控系统
	 */
   	@TableField(value = "excess_flag")
	private Integer excessFlag;

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

	/**
	 * 指定发送数据的帐号，若没有指定帐号，则所有用户都发送
	 */
   	@TableField(value = "send_account")
	private String sendAccount;

	/**
	 * 官方网址
	 */
   	@TableField(value = "official_site")
	private String officialSite;

}
