package com.easy.service.impl;

import com.easy.common.WebConfig;
import com.easy.dao.SysRunLogDao;
import com.easy.entity.SysRunLog;
import com.easy.service.SysRunLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
@Service
public class SysRunLogServiceImpl implements SysRunLogService {

    @Autowired
    private SysRunLogDao sysRunLogDao;

    @Override
    public void addRunLog(Integer logType, String logContent) {
        SysRunLog sysRunLog = new SysRunLog();
        sysRunLog.setLogContent(logContent);
        sysRunLog.setLogType(logType);
        sysRunLog.setLogTime(new Date());
        sysRunLog.setServerId(WebConfig.getServerId());
        sysRunLogDao.insert(sysRunLog);
    }

    @Override
    public String getLogContent(int logType, String logContent) {
        return sysRunLogDao.getLogContent(logType, logContent);
    }

}
