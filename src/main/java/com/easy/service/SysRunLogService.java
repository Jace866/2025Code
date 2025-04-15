package com.easy.service;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
public interface SysRunLogService {

    void addRunLog(Integer logType, String logContent);

    String getLogContent(int runLogTypeGenId, String s);

}
