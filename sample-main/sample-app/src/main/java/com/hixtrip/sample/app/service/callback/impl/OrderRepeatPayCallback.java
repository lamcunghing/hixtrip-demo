package com.hixtrip.sample.app.service.callback.impl;

import com.hixtrip.sample.app.service.callback.OrderPayCallback;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单重复支付回调
 *
 * @author Xxx
 */
@Component
@Slf4j
public class OrderRepeatPayCallback extends OrderPayCallback {

    @Override
    protected void doCallback(CommandPay commandPay) {
        log.warn("重复支付回调...");
    }

    @Override
    public boolean isSupport(CommandPayDTO commandPayDTO) {
        return commandPayDTO.getRepeat() != null && commandPayDTO.getRepeat();
    }
}
