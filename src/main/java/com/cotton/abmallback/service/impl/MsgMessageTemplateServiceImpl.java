package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.MsgMessageTemplate;
import com.cotton.abmallback.service.MsgMessageTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MsgMessageTemplate
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgMessageTemplateServiceImpl extends BaseServiceImpl<MsgMessageTemplate> implements MsgMessageTemplateService {
}