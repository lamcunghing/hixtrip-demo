package com.hixtrip.sample.app.service.callback.impl;

import com.hixtrip.sample.app.service.callback.OrderPayCallback;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 订单支付成功回调
 *
 * @author Xxx
 */
@Component
public class OrderSuccessPayCallback extends OrderPayCallback {

    @Override
    protected void doCallback(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }

    @Override
    public boolean isSupport(CommandPayDTO commandPayDTO) {
        return PayStatusEnum.SUCCESS_PAY.getValue().equals(commandPayDTO.getPayStatus());
    }
}
