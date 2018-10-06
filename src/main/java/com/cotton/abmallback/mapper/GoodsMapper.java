package com.cotton.abmallback.mapper;

import com.cotton.abmallback.model.Goods;
import com.cotton.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface GoodsMapper extends BaseMapper<Goods> {
    @Update("update goods set is_on_sell = #{isOnSale} where id=#{goodsId} and is_deleted = 0")
    void setGoodsOnSaleStatus(
            @Param(value = "goodsId") long goodsId,
            @Param(value = "isOnSale") int isOnSale
    );
}