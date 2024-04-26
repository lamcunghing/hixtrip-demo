package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.service.callback.OrderPayCallback;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderConvertor orderConvertor;

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public String createOrder(CommandOderCreateDTO createDTO) {
        //1、参数校验
        Assert.hasText(createDTO.getSkuId(), "skuId为空");
        Assert.isTrue(createDTO.getAmount() != null && createDTO.getAmount() > 0, "购买数量为空");

        //2、将请求对象转成实体
        Order order = orderConvertor.orderCreateDTOToOrder(createDTO);

        //3、调用领域服务对象进行订单的创建，并返回订单id
        orderDomainService.createOrder(order);
        return order.getId();
    }

    @Override
    public String payCallback(OrderPayCallback orderPayCallback, CommandPayDTO commandPayDTO) {
        return orderPayCallback.callback(commandPayDTO);
    }


}
