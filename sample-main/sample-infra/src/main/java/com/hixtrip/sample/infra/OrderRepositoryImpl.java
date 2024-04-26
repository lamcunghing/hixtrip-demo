package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 订单仓储实现
 *
 * @author xxx
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderDOConvertor orderDOConvertor;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void save(Order order) {
        OrderDO orderDO = orderDOConvertor.orderToOrderDO(order);
        if(orderDO.getId() == null){
            orderMapper.insert(orderDO);
            order.setId(String.valueOf(order.getId()));
        }else{
            orderMapper.updateById(orderDO);
        }
    }

    @Override
    public Order get(String id) {
        Long orderId = Long.valueOf(id);
        OrderDO orderDO = orderMapper.selectById(orderId);
        return orderDOConvertor.orderDOToOrder(orderDO);
    }


}
