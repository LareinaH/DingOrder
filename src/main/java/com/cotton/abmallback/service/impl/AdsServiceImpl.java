package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.enumeration.AdTypeEnum;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.Ads;
import com.cotton.abmallback.service.AdsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Ads
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdsServiceImpl extends BaseServiceImpl<Ads> implements AdsService {

    @Override
    public List<Ads> queryBanner() {

        Example example = new Example(Ads.class);
        example.setOrderByClause("sort desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adType", AdTypeEnum.BANNER.name());
        criteria.andEqualTo("isDeleted","0");

        return queryList(example);
    }

    @Override
    public Ads queryInvitingCode(String level) {
        Example example = new Example(Ads.class);
        example.setOrderByClause("sort desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adType", AdTypeEnum.INVITING_CODE_BACKGROUND.name());
        criteria.andEqualTo("isDeleted","0");
        criteria.andEqualTo("level",level);

        return queryList(example).get(0);
    }

    @Override
    public Ads queryTeamSystem() {
        Example example = new Example(Ads.class);
        example.setOrderByClause("sort desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adType", AdTypeEnum.TEAM_SYSTEM.name());
        criteria.andEqualTo("isDeleted","0");

        List<Ads> adsList = queryList(example);

        if(null == adsList || adsList.isEmpty()){
            return null;
        }

        return adsList.get(0);
    }
}