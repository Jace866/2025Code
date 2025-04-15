package com.easy.service.impl;

import com.easy.common.WebConfig;
import com.easy.service.SysRunLogService;
import com.easy.service.ThreadService;
import com.easy.service.UserService;
import com.easy.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadServiceImpl implements ThreadService {
    public static final int RUN_LOG_TYPE_ONLINE_PAYMENT=30; //在线充值
    public static final int RUN_LOG_TYPE_WITHDRAW=32;       //提款
    public static final int RUN_LOG_TYPE_BET=33;            //投注
    public static final int RUN_LOG_TYPE_UPLOAD=34;         //上传(图片)
    public static final int RUN_LOG_TYPE_REFRESH=35;        //刷新缓存数据
    public static final int RUN_LOG_TYPE_CHATROOM=50;       //刷新聊天室配置信息
    public static final int RUN_LOG_TYPE_MOBILE_OTHERS=72;  //手机其它
    public static final int RUN_LOG_TYPE_CHATROOM_OTHERS = 73;  //聊天室其它
    public static final int RUN_LOG_TYPE_GEN_ID = 77;         //ID生成
    public static final int RUN_LOG_TYPE_API_INVOKE=78;     //调用第三方API
    public static final int RUN_LOG_TYPE_SERVER_STARTUP = 80; //服务器启动
    public static ExecutorService servicePool = Executors.newSingleThreadExecutor();

    @Autowired
    private SysRunLogService sysRunLogService;
    @Autowired
    private UserService userService;

    //服务器启动日志
    @Override
    public void addStartupLog(String logContent) {
        addRunLog(RUN_LOG_TYPE_SERVER_STARTUP, logContent);
    }

    //服务器关闭日志
    @Override
    public void addShutdownLog() {
        new Thread(() -> {
            try {
                String sql = "INSERT INTO sys_run_log(server_id,log_type,log_time,log_content) VALUES(?,?,?,?)";
                String url = WebConfig.getJdbcUrl();
                String user = WebConfig.getUser();
                String password = WebConfig.getPassword();
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);
                Timestamp nowtime = new Timestamp(System.currentTimeMillis());
                conn.setAutoCommit(false);
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1, String.valueOf(WebConfig.getServerId()));
                psmt.setInt(2, 81);
                psmt.setTimestamp(3, nowtime);
                psmt.setString(4, "聊天室web应用服务器已关闭，当前时间=" + String.format("%1$tY-%1$tm-%1$td %1$tT %1$tL", nowtime));
                psmt.execute();
                conn.commit();
                psmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    @Override
    public void addLoginLog(Integer userId, String name, int tryFlag, String loginIp, String domain, String remark, String device, int serverId){
        addLoginLog(userId, name, tryFlag, loginIp, domain, remark, device, serverId, 0, null, null, null, "2");
    }

    //生成登录日志
    @Override
    public void addLoginLog(Integer userId, String name, int tryFlag, String loginIp, String domain, String remark, String device, int serverId, int merchantId, HttpSession session, String token, String userAgent, String way){
        servicePool.execute(()->{
            userService.addUserLog(userId, tryFlag, Integer.parseInt(way), loginIp, domain, remark, device,token);
            if( session !=null ){
                userService.addOnlineUser(serverId, session.getId(), userId, name, tryFlag, Integer.parseInt(way), loginIp, domain, merchantId, token);
                //Db.update("UPDATE user_info SET login_time=?,login_ip=? WHERE id=?", DateUtil.nowtime(), loginIp,userId);
                if(userAgent!=null){
                    userService.updateLoginInfo(DateUtil.nowtime(), loginIp, userAgent, userId);
                }else{
                    userService.updateLoginInfo(DateUtil.nowtime(), loginIp, null, userId);
                }
            }
        });
    }

    //生成运行日志 异步生成通用方法，免除调用时 需要定义final局部变量
    @Override
    public void addRunLog(int logType, String logContent) {
        servicePool.execute(() -> {
            sysRunLogService.addRunLog(logType, logContent);
        });
    }

}



