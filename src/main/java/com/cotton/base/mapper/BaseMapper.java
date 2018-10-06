package com.cotton.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.cotton.base.model.BaseModel;

/**
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

public interface BaseMapper<ModelType extends BaseModel> extends Mapper<ModelType>, MySqlMapper<ModelType> {
}
