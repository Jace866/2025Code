package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.LotteryInfo;
import com.easy.vo.LotteryInfoVo;
import com.easy.vo.LotteryInitVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Mapper
public interface LotteryInfoDao extends BaseMapper<LotteryInfo> {
    List<LotteryInitVo> initFindVos();

    List<LotteryInitVo> getLotteryCodeName();

    List<LotteryInfo> getLotteryBeanList();

    List<LotteryInfoVo> getLotteryExtList();

    List<LotteryInitVo> getLotteryListByGameIds(String codes);
}
