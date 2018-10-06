package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.ShopActivityGoods;
import com.cotton.abmallback.service.ShopActivityGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ShopActivityGoods
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopActivityGoodsServiceImpl extends BaseServiceImpl<ShopActivityGoods> implements ShopActivityGoodsService {
}