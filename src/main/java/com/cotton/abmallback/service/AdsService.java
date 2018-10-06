package com.cotton.abmallback.service;

import com.cotton.base.service.BaseService;
import com.cotton.abmallback.model.Ads;

import java.util.List;

/**
 * Ads
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
public interface AdsService extends BaseService<Ads> {

    List<Ads> queryBanner();

    Ads queryInvitingCode(String level);

    Ads queryTeamSystem();
}
