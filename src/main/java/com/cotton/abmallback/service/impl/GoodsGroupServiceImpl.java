package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.GoodsGroup;
import com.cotton.abmallback.service.GoodsGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GoodsGroup
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsGroupServiceImpl extends BaseServiceImpl<GoodsGroup> implements GoodsGroupService {
}