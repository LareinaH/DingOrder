package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.MsgPlatformMessage;
import com.cotton.abmallback.service.MsgPlatformMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MsgPlatformMessage
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgPlatformMessageServiceImpl extends BaseServiceImpl<MsgPlatformMessage> implements MsgPlatformMessageService {
}