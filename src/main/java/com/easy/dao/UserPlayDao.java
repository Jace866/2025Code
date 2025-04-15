package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.UserPlay;
import com.easy.vo.UserPlayVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Mapper
public interface UserPlayDao extends BaseMapper<UserPlay> {
    void updateIsEndByPlayNo(int isSend, String playNo);

    List<UserPlayVo> findHistoryYesToday(String begin, String end);

    List<UserPlayVo> findHistoryToday(String begin, String end);
}
