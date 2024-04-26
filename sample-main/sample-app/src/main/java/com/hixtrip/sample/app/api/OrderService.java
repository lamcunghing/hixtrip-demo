package com.hixtrip.sample.app.api;

import com.hixtrip.sample.app.service.callback.OrderPayCallback;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param createDTO 创建订单DTO
     * @return 订单id
     */
    String createOrder(CommandOderCreateDTO createDTO);

    /**
     * 订单支付回调
     *
     * @param orderPayCallback 回调对象
     * @param commandPayDTO 订单支付结果
     * @return
     */
    String payCallback(OrderPayCallback orderPayCallback, CommandPayDTO commandPayDTO);


}
