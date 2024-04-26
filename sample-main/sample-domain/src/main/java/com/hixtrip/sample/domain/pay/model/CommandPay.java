package com.hixtrip.sample.domain.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

    /** 记录id */
    private String id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String payStatus;

    /** 用户名 */
    private String payUserName;


}