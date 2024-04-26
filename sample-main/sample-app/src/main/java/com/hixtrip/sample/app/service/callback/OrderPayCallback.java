package com.hixtrip.sample.app.service.callback;

import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付回调接口
 *
 * @author Xxx
 */
public abstract class OrderPayCallback {

    @Autowired
    private CommandPayConvertor commandPayConvertor;

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    protected OrderDomainService orderDomainService;

    public String callback(CommandPayDTO commandPayDTO){
        //1、幂等性校验
//        CommandPay commandPay = null;
//        if(commandPay != null){
//            return commandPay.getId();
//        }

        //2、将DTO转成支付结果实体对象
        CommandPay commandPay = commandPayConvertor.commandPayDTOToCommandPay(commandPayDTO);

        //3、调用具体的回调逻辑
        this.doCallback(commandPay);

        //4、记录回调结果
        payDomainService.payRecord(commandPay);

        return commandPay.getId();
    }

    /**
     * 具体的回调逻辑
     *
     * @param commandPay 支付结果
     */
    protected abstract void doCallback(CommandPay commandPay);

    /**
     * 是否支持指定的支付结果
     *
     * @return true，false
     */
    public abstract boolean isSupport(CommandPayDTO commandPayDTO);


}
