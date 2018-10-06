package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.AccountMoneyFlow;
import com.cotton.abmallback.service.AccountMoneyFlowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountMoneyFlow
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountMoneyFlowServiceImpl extends BaseServiceImpl<AccountMoneyFlow> implements AccountMoneyFlowService {
}