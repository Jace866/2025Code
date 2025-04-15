package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.User;
import com.easy.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Mapper
public interface UserInfoDao extends BaseMapper<User> {
    UserVo findUserAndCurrencyType(int merchantId, String loginname);

    void updateLoginInfo(String nowtime, String loginIp, String userAgent, Integer userId);

    void updateCurrencyIdById(Integer currencyTypeId, int uid);

    void updatePtypeById(String oddtype, int uid);
}
