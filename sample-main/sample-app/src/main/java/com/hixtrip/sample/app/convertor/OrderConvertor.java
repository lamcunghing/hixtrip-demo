package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.order.model.Order;
import org.mapstruct.Mapper;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper(componentModel = "spring")
public interface OrderConvertor {

    /**
     * 将DTO转成订单实体对象
     * @param createDTO DTO对象
     * @return 实体对象
     */
    Order orderCreateDTOToOrder(CommandOderCreateDTO createDTO);


}
