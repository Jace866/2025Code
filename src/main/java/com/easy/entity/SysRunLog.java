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
@TableName (value = "sys_run_log")
@Accessors(chain = true)
public class SysRunLog implements Serializable {
	private static final long serialVersionUID =  5894020319828851947L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 服务器Id:10-管理主服务器,11--管理从,20-代理主,21--代理从,30-前台主,31--前台从,40-手机主,41--手机从,90-总控主
	 */
   	@TableField(value = "server_id")
	private Integer serverId;

	/**
	 * 日志类型:10-数据清理,11-数据迁移,12-后台出款,19-后台其它;30-在线充值,31-其它充值,32-提款,33-投注, 80-服务器启动,81-服务器关闭
	 */
   	@TableField(value = "log_type")
	private Integer logType;

	/**
	 * 日志时间
	 */
   	@TableField(value = "log_time")
	private Date logTime;

	/**
	 * 日志内容
	 */
   	@TableField(value = "log_content")
	private String logContent;

}
