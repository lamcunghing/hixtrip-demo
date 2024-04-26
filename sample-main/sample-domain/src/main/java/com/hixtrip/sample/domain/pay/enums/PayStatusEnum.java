package com.hixtrip.sample.domain.pay.enums;

import lombok.Getter;

/**
 * 支付状态枚举
 *
 * @author Xxx
 */
@Getter
public enum PayStatusEnum {

    UN_PAY("0","未支付"),
    SUCCESS_PAY ("1","支付成功"),
    FAIL_PAY("2","支付失败");

    private String value;
    private String label;

    PayStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
