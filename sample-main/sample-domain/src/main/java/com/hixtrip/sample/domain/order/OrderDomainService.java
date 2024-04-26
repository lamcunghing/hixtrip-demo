package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;

    /**
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        //1、订单初始化
        order.init();

        //2、计算总金额
        order.calculateMoney(commodityDomainService::getSkuPrice);

        //3、修改库存：增加预占数，扣减可售数
        Long withholdingQuantity = Long.valueOf(order.getAmount());
        Long sellableQuantity = withholdingQuantity * -1;
        Boolean changeResult = inventoryDomainService.changeInventory(order.getSkuId(), sellableQuantity, withholdingQuantity, 0L);
        if(changeResult != null && changeResult){
            //此处应该替换为一个能代表业务意义的异常
            throw new RuntimeException("库存不足");
        }

        //4、保存订单
        orderRepository.save(order);
    }

    /**
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //1、从仓储加载订单实体
        Order order = orderRepository.get(commandPay.getOrderId());

        //2、订单支付成功：修改相关信息
        order.onPaySuccess(commandPay);

        //3、修改库存：增加占用数，扣减预占数
        Long occupiedQuantity = Long.valueOf(order.getAmount());
        Long withholdingQuantity = occupiedQuantity * -1;
        inventoryDomainService.changeInventory(order.getSkuId(), 0L, withholdingQuantity, occupiedQuantity);

        //4、保存订单
        orderRepository.save(order);
    }

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //1、从仓储加载订单实体
        Order order = orderRepository.get(commandPay.getOrderId());

        //2、订单支付成功：修改相关信息
        order.onPayFail(commandPay);

        //3、修改库存：扣减预占数，增加可售数
        Long sellableQuantity = Long.valueOf(order.getAmount());
        Long withholdingQuantity = sellableQuantity * -1;
        inventoryDomainService.changeInventory(order.getSkuId(), sellableQuantity, withholdingQuantity, 0L);

        //4、保存订单
        orderRepository.save(order);
    }
}
