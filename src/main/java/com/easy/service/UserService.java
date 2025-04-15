package com.easy.service;

import com.easy.entity.User;
import com.easy.vo.UserVo;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
public interface UserService {
    User findFirstByName(String username);

    User findFirstByTypeAndName(Integer type, String aguser);

    String doEncrypt(String username, String password);

    String doEncrypt2(String username, String password);

    void save(User user);

    UserVo findUserAndCurrencyType(int merchantId, String loginname);

    User findUserById(Integer uid);

    void addUserLog(Integer userId, int tryFlag, int mobileFlag, String loginIp, String domain, String remark, String device, String token);

    void addOnlineUser(int serverId, String sessionId, Integer userId, String name, int tryFlag, int mobileFlag, String loginIp, String domain, int merchantId, String token);

    void updateLoginInfo(String nowtime, String loginIp, String userAgent, Integer userId);

    void updateCurrencyIdById(Integer id, int uid);

    void updatePtypeById(String oddtype, int uid);

    void kickoutByUid(int uid);
}
