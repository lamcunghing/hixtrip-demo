package com.hixtrip.sample.app.service.callback;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单支付回调工厂
 *
 * @author Xxx
 */
@Component
@Slf4j
public class OrderPayCallbackFactory implements ApplicationContextAware {

    /** 回调对象缓存 */
    private List<OrderPayCallback> callbackCache = new ArrayList<>();

    /** 表示不存在的回调，当不需要进行回调处理时，可使用该对象 */
    private static final OrderPayCallback NONE = new OrderPayCallback() {
        @Override
        protected void doCallback(CommandPay commandPay) {
            log.debug("订单支付结果空回调......");
        }

        @Override
        public boolean isSupport(CommandPayDTO commandPayDTO) {
            return true;
        }
    };

    /**
     * 获取订单支付回调对象
     * @param commandPayDTO 支付结果DTO
     * @return 回调对象。如果没有找到匹配的回调，则返回NONE对象作为占位
     */
    public OrderPayCallback get(CommandPayDTO commandPayDTO){
        OrderPayCallback candidate = null;
        for(OrderPayCallback callback : callbackCache){
            if(callback.isSupport(commandPayDTO)){
                candidate = callback;
                break;
            }
        }
        if(candidate == null){
            candidate = NONE;
        }
        return candidate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //扫描IOC容器中的所有回调，按照优先级排序，并缓存
        Map<String, OrderPayCallback> beanMap = applicationContext.getBeansOfType(OrderPayCallback.class);
        List<OrderPayCallback> orderPayCallbacks = new ArrayList<>(beanMap.values());
        AnnotationAwareOrderComparator.sort(orderPayCallbacks);
        this.callbackCache = orderPayCallbacks;
    }

}
