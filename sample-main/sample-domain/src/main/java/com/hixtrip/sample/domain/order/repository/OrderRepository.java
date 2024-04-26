package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 * 订单仓储接口
 *
 * @author xxx
 */
public interface OrderRepository {

    /**
     * 保存订单
     *
     * @param order
     */
    void save(Order order);

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    Order get(String id);

}
