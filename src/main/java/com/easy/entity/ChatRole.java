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
 * @Date 2025-04-12 
 */

@Data
@TableName (value = "chat_role")
@Accessors(chain = true)
public class ChatRole implements Serializable {
	private static final long serialVersionUID =  1086772925712473407L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 角色名
	 */
   	@TableField(value = "name")
	private String name;

	/**
	 * 平台编码
	 */
   	@TableField(value = "plat_code")
	private String platCode;

	/**
	 * 角色类型:0-游客, 1-会员, 2-管理员
	 */
   	@TableField(value = "type")
	private Integer type;

	/**
	 * 会员等级:1-普通会员, 2-白银会员, 3-黄金会员, 4-铂金会员, 5-钻石会员, 6-至尊会员
	 */
   	@TableField(value = "level")
	private Integer level;

	/**
	 * 下注背景颜色
	 */
   	@TableField(value = "bet_bgcolor")
	private String betBgcolor;

	/**
	 * 聊天背景颜色
	 */
   	@TableField(value = "bg_color")
	private String bgColor;

	/**
	 * 字体颜色
	 */
   	@TableField(value = "text_color")
	private String textColor;

	/**
	 * 消息最大长度
	 */
   	@TableField(value = "max_length")
	private Integer maxLength;

	/**
	 * 踢人
	 */
   	@TableField(value = "is_kick")
	private Integer isKick;

	/**
	 * 发言
	 */
   	@TableField(value = "is_speak")
	private Integer isSpeak;

	/**
	 * 私聊
	 */
   	@TableField(value = "is_chat")
	private Integer isChat;

	/**
	 * 发图
	 */
   	@TableField(value = "is_send_img")
	private Integer isSendImg;

	/**
	 * 发红包
	 */
   	@TableField(value = "is_send_packet")
	private Integer isSendPacket;

	/**
	 * 打赏
	 */
   	@TableField(value = "is_send_reward")
	private Integer isSendReward;

	/**
	 * 默认
	 */
   	@TableField(value = "is_default")
	private Integer isDefault;

	/**
	 * 禁言
	 */
   	@TableField(value = "is_gag")
	private Integer isGag;

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

	/**
	 * 描述
	 */
   	@TableField(value = "remark")
	private String remark;

}
