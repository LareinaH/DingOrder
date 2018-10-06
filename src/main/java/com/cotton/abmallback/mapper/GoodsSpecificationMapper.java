package com.cotton.abmallback.mapper;

import com.cotton.abmallback.model.GoodsSpecification;
import com.cotton.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsSpecificationMapper extends BaseMapper<GoodsSpecification> {

    @Select("select distinct(goods_specification_name) from goods_specification order by goods_specification_name")
    List<String> getSpecUnitList();

    @Update("update goods_specification set is_on_sell = #{isOnSale} where goods_id=#{goodsId} and is_deleted = 0")
    void setGoodsOnSaleStatus(
            @Param(value = "goodsId") long goodsId,
            @Param(value = "isOnSale") int isOnSale
    );
}