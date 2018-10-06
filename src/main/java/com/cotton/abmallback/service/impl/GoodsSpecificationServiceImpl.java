package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.mapper.GoodsSpecificationMapper;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.GoodsSpecification;
import com.cotton.abmallback.service.GoodsSpecificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GoodsSpecification
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsSpecificationServiceImpl extends BaseServiceImpl<GoodsSpecification> implements GoodsSpecificationService {


    private  final GoodsSpecificationMapper goodsSpecificationMapper;

    public GoodsSpecificationServiceImpl(GoodsSpecificationMapper goodsSpecificationMapper) {
        this.goodsSpecificationMapper = goodsSpecificationMapper;
    }

    @Override
    public List<String> getSpecUnitList() {
        return goodsSpecificationMapper.getSpecUnitList();
    }
}