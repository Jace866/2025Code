package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.SysParameter;
import com.easy.vo.SysParameterVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : hello_@
 * @Date : 2024/6/27
 */
@Mapper
public interface SysParameterDao extends BaseMapper<SysParameter> {

    String getParamValue(@Param("type") String type, @Param("code") String code);

    String getParamValue3(@Param("type") String type, @Param("code") String code, @Param("status") int status);

    void updateValue(@Param("value") int value, @Param("type") String type, @Param("code") String code);

    List<SysParameterVo> getAllParameter();

    List<SysParameterVo> getParameterByType(String type);

    List<SysParameter> getUserBankcodeList(String type, @Param("status")int status);
}
