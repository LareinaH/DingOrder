package com.cotton.abmallback.model.vo.admin;

import com.cotton.abmallback.model.ShopActivities;
import com.cotton.abmallback.model.ShopActivityConfig;
import com.cotton.abmallback.model.ShopActivityGoods;

import java.util.List;

/**
 * ShopActivitiesVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/5
 */
public class ShopActivitiesVO extends ShopActivities {

    private ShopActivityGoods shopActivityGoods;

    private List<ShopActivityConfig> shopActivityConfigList;

    public ShopActivityGoods getShopActivityGoods() {
        return shopActivityGoods;
    }

    public void setShopActivityGoods(ShopActivityGoods shopActivityGoods) {
        this.shopActivityGoods = shopActivityGoods;
    }

    public List<ShopActivityConfig> getShopActivityConfigList() {
        return shopActivityConfigList;
    }

    public void setShopActivityConfigList(List<ShopActivityConfig> shopActivityConfigList) {
        this.shopActivityConfigList = shopActivityConfigList;
    }
}
