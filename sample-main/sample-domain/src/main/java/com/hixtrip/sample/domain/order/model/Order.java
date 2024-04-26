package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * 订单表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    /** 未删除标识 */
    private static final Long UN_DEL_FLAG = 0L;
    /** 删除标识 */
    private static final Long DEL_FLAG = 1L;

    /**
     * 订单号
     */
    private String id;


    /**
     * 购买人
     */
    private String userId;


    /**
     * SkuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态。0-未支付，1-支付成功，2-支付失败
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Long delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    /**
     * 订单初始化
     */
    public void init(){
        //this.id = UUID.randomUUID().toString().replaceAll("-","");
        this.payStatus = PayStatusEnum.UN_PAY.getValue();
        this.delFlag = UN_DEL_FLAG;
        this.createTime = LocalDateTime.now();
    }

    /**
     * 计算总金额
     *
     * @param skuPriceProvider 商品单价提供者
     */
    public void calculateMoney(Function<String, BigDecimal> skuPriceProvider){
        BigDecimal skuPrice = skuPriceProvider.apply(this.getSkuId());
        this.money = skuPrice.multiply(new BigDecimal(amount));
    }


    /**
     * 支付成功调用
     *
     * @param commandPay 支付结果
     */
    public void onPaySuccess(CommandPay commandPay){
        this.onPay(commandPay, PayStatusEnum.SUCCESS_PAY.getValue());
    }

    /**
     * 支付失败调用
     *
     * @param commandPay 支付结果
     */
    public void onPayFail(CommandPay commandPay){
        this.onPay(commandPay, PayStatusEnum.FAIL_PAY.getValue());
    }

    /**
     * 支付结束调用
     *
     * @param commandPay 支付结果
     * @param payStatus 支付状态
     */
    private void onPay(CommandPay commandPay,String payStatus){
        LocalDateTime now = LocalDateTime.now();
        this.updateTime = now;
        this.payTime = now;
        this.updateBy = commandPay.getPayUserName();
        this.payStatus = payStatus;
    }

}
