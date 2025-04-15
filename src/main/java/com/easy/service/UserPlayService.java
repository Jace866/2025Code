package com.easy.service;

import com.easy.entity.UserPlay;
import com.easy.vo.UserPlayVo;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
public interface UserPlayService {
    void updateIsEndByPlayNo(int isSend, String playNo);

    List<UserPlayVo> findHistoryYesToday(String begin, String end);

    List<UserPlayVo> findHistoryToday(String begin, String end);
}
