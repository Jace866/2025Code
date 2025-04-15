package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.LotteryRatioInstant;
import com.easy.vo.LotteryRatioInstantInitVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Mapper
public interface LotteryRatioInstantDao extends BaseMapper<LotteryRatioInstant> {
    List<LotteryRatioInstantInitVo> findVos();
}
