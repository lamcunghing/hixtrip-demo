package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 * todo 自由实现
 */
@Mapper(componentModel = "spring")
public interface OrderDOConvertor {

//    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    /**
     * 订单实体转成DO对象
     * @param order 订单实体对象
     * @return 订单DO对象
     */
    OrderDO orderToOrderDO(Order order);

    /**
     * 订单DO转成实体对象
     * @param orderDO 订单DO对象
     * @return 订单实体对象
     */
    Order orderDOToOrder(OrderDO orderDO);

}
