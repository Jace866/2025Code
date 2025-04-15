package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.common.WebConfig;
import com.easy.dao.UserInfoDao;
import com.easy.dao.UserLogDao;
import com.easy.dao.UserOnlineDao;
import com.easy.entity.User;
import com.easy.entity.UserLog;
import com.easy.entity.UserOnline;
import com.easy.service.UserService;
import com.easy.util.DateUtil;
import com.easy.util.IpAddressUtil;
import com.easy.util.SecurityUtil;
import com.easy.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserLogDao userLogDao;
    @Autowired
    private UserOnlineDao userOnlineDao;

    @Override
    public User findFirstByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        return userInfoDao.selectOne(queryWrapper);
    }

    @Override
    public User findFirstByTypeAndName(Integer type, String aguser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", aguser);
        queryWrapper.eq("type", type);
        return userInfoDao.selectOne(queryWrapper);
    }
    @Override
    public String doEncrypt(String username, String password) {
        password = SecurityUtil.md5(password);
        password = SecurityUtil.md5(password);
        String tmp = SecurityUtil.md5(username);
        password = SecurityUtil.md5(username + "AA" + password + "BB" + tmp);
        return password;
    }
    @Override
    public String doEncrypt2(String username, String password) {
        String tmp = SecurityUtil.md5(username);
        password = SecurityUtil.md5(username + "AA" + password + "BB" + tmp);
        return password;
    }

    @Override
    public void save(User user) {
        userInfoDao.insert(user);
    }

    @Override
    public UserVo findUserAndCurrencyType(int merchantId, String loginname) {
        return userInfoDao.findUserAndCurrencyType(merchantId, loginname);
    }

    @Override
    public User findUserById(Integer uid) {
        return userInfoDao.selectById(uid);
    }

    @Override
    @Transactional
    public void addUserLog(Integer userId, int tryFlag, int mobileFlag, String loginIp, String domain, String remark, String device, String token) {
        try{
            String loginAddress= IpAddressUtil.getRealAddress(loginIp);
            UserLog userLog = new UserLog();
            userLog.setServerIdIn(WebConfig.getServerId());
            userLog.setUserId(userId);
            userLog.setTryFlag(tryFlag);
            userLog.setMobileFlag(mobileFlag);
            userLog.setLoginIp(loginIp);
            userLog.setLoginAddress(loginAddress);
            userLog.setRemark(remark);
            userLog.setDevice(device);
            userLog.setLoginDomain(domain);
            userLog.setToken(token);
            userLog.setLoginTime(new Date());
            userLogDao.insert(userLog);
        }catch(Exception e){
            log.error("生成登录日志发生错。原因："+e.getMessage());
        }
    }

    @Override
    public void addOnlineUser(int serverId, String sessionId, Integer userId, String name, int tryFlag, int mobileFlag, String loginIp, String domain, int merchantId, String token) {
        String randNum = this.getStringRandom(32);
        Date nowtime=new Date();
        UserOnline record=userOnlineDao.findBySessionIdAndUserId(sessionId, userId);
        if( record==null ){
            record = new UserOnline()
                    .setServerId(serverId)
                    .setSessionId(sessionId)
                    .setUserId(userId)
                    .setName(name)
                    .setLoginIp(loginIp)
                    .setLoginDomain(domain)
                    .setTryFlag(tryFlag)
                    .setMobileFlag(mobileFlag)
                    .setMerchantId(merchantId)
                    .setToken(token)
                    .setRandomNum(randNum)
                    .setActionTime(nowtime)
                    .setLoginTime(nowtime);
            userOnlineDao.insert(record);
        }else{
            record.setLoginIp(loginIp)
                    .setLoginDomain(domain)
                    .setToken(token)
                    .setRandomNum(randNum)
                    .setLoginTime(nowtime);
            userOnlineDao.updateById(record);
        }
    }

    @Override
    public void updateLoginInfo(String nowtime, String loginIp, String userAgent, Integer userId) {
        userInfoDao.updateLoginInfo(DateUtil.nowtime(), loginIp, userAgent, userId);
    }

    @Override
    public void updateCurrencyIdById(Integer currencyType_id, int uid) {
        userInfoDao.updateCurrencyIdById(currencyType_id, uid);
    }

    @Override
    public void updatePtypeById(String oddtype, int uid) {
        userInfoDao.updatePtypeById(oddtype, uid);
    }

    @Override
    public void kickoutByUid(int uid) {
        userOnlineDao.kickoutByUid(uid);
    }

    //生成随机数字和字母
    private String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }
}
