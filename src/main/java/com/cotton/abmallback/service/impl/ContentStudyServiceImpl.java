package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.ContentStudy;
import com.cotton.abmallback.service.ContentStudyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ContentStudy
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentStudyServiceImpl extends BaseServiceImpl<ContentStudy> implements ContentStudyService {
}