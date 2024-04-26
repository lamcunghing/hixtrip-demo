package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 库存实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {

    /** skuId*/
    private String skuId;

    /** 可售库存量 */
    private Long sellableAmount;

    /** 预占库存量 */
    private Long withholdingAmount;

    /** 占有库存量 */
    private Long occupiedAmount;

}
