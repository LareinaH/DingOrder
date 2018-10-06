package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.SmsCaptcha;
import com.cotton.abmallback.service.SmsCaptchaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SmsCaptcha
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmsCaptchaServiceImpl extends BaseServiceImpl<SmsCaptcha> implements SmsCaptchaService {
}