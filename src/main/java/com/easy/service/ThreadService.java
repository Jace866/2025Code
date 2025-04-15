package com.easy.service;


import javax.servlet.http.HttpSession;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
public interface ThreadService {
    //服务器启动日志
    void addStartupLog(String logContent);

    //服务器关闭日志
    void addShutdownLog();

    void addLoginLog(Integer userId, String name, int tryFlag, String loginIp, String domain, String remark, String device, int serverId);

    //生成登录日志
    void addLoginLog(Integer userId, String name, int tryFlag, String loginIp, String domain, String remark, String device, int serverId, int merchantId, HttpSession session, String token, String userAgent, String way);

    //生成运行日志 异步生成通用方法，免除调用时 需要定义final局部变量
    void addRunLog(int logType, String logContent);

}
