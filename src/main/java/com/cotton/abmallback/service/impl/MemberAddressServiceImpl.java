package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.MemberAddress;
import com.cotton.abmallback.service.MemberAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberAddress
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberAddressServiceImpl extends BaseServiceImpl<MemberAddress> implements MemberAddressService {
}