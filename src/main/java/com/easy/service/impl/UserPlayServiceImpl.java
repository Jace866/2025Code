package com.easy.service.impl;

import com.easy.dao.UserPlayDao;
import com.easy.entity.UserPlay;
import com.easy.service.UserPlayService;
import com.easy.vo.UserPlayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class UserPlayServiceImpl implements UserPlayService {
    @Autowired
    private UserPlayDao userPlayDao;

    @Override
    public void updateIsEndByPlayNo(int isSend, String playNo) {
        userPlayDao.updateIsEndByPlayNo(isSend, playNo);
    }

    @Override
    public List<UserPlayVo> findHistoryYesToday(String begin, String end) {
        return userPlayDao.findHistoryYesToday(begin, end);
    }

    @Override
    public List<UserPlayVo> findHistoryToday(String begin, String end) {
        return userPlayDao.findHistoryToday(begin, end);
    }
}
