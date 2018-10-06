package com.cotton.abmallback.service;

import com.cotton.abmallback.model.GoodsSpecification;
import com.cotton.base.service.BaseService;

import java.util.List;

/**
 * GoodsSpecification
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/22
 */
public interface GoodsSpecificationService extends BaseService<GoodsSpecification> {
    public List<String> getSpecUnitList();
}
