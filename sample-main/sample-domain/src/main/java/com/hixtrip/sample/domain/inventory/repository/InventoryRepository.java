package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 获取指定sku的库存量
     * @param skuId
     */
    Inventory get(String skuId);

    /**
     * 修改库存量
     *
     * @param skuId 库存id
     * @param sellableQuantity 可售的变更量
     * @param withholdingQuantity 预占的变更量
     * @param occupiedQuantity 占有的变更量
     * @return 是否修改成功
     */
    Boolean update(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);


}
