package com.hixtrip.sample.app.service.callback.impl;

import com.hixtrip.sample.app.service.callback.OrderPayCallback;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 订单支付失败回调
 *
 * @author Xxx
 */
@Component
public class OrderFailPayCallback extends OrderPayCallback {

    @Override
    protected void doCallback(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
    }

    @Override
    public boolean isSupport(CommandPayDTO commandPayDTO) {
        return PayStatusEnum.FAIL_PAY.getValue().equals(commandPayDTO.getPayStatus());
    }
}
