package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.ChatRoleDao;
import com.easy.entity.ChatRole;
import com.easy.service.ChatRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class ChatRoleServiceImpl implements ChatRoleService {
    @Autowired
    private ChatRoleDao chatRoleDao;

    @Override
    public int queryIdByType(int type) {
        QueryWrapper<ChatRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        ChatRole chatRole = chatRoleDao.selectOne(queryWrapper);
        return chatRole.getId();
    }
}
