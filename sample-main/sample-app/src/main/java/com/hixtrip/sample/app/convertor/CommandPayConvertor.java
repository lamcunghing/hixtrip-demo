package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.mapstruct.Mapper;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper(componentModel = "spring")
public interface CommandPayConvertor {

    CommandPay commandPayDTOToCommandPay(CommandPayDTO commandPayDTO);

}
