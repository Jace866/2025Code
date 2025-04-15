package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.LotteryMethod;
import com.easy.vo.LotteryMethodInitVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Mapper
public interface LotteryMethodDao extends BaseMapper<LotteryMethod> {

    List<LotteryMethodInitVo> findListVo();
}
