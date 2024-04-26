package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    private static final String ORDER_INVENTORY_KEY = "order-inventory";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Inventory get(String skuId) {
        String sellableField = String.format("%s-sellable",skuId);
        String withholdingField = String.format("%s-withholding",skuId);
        String occupiedField = String.format("%s-occupied",skuId);
        //1、调用redis获取各类型的库存
        HashOperations<String, String, Long> opsForHash = redisTemplate.opsForHash();
        List<Long> values = opsForHash.multiGet(ORDER_INVENTORY_KEY, Arrays.asList(sellableField, withholdingField, occupiedField));
        //2、构建实体返回
        return new Inventory(skuId, values.get(0), values.get(1), values.get(2));

    }

    /**
     * 修改库存量
     *
     * 库存数据采用hash结构存储，key为order-inventory，value：
     * sku1-sellable:10
     * sku1-withholding:20
     * sku1-occupied:10
     *
     * 这里将一个三种库存分为三个field进行存储，相关性不是太好
     *
     * @param skuId 库存id
     * @param sellableQuantity 可售的变更量
     * @param withholdingQuantity 预占的变更量
     * @param occupiedQuantity 占有的变更量
     * @return 变更结果
     */
    public Boolean update(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity){
        String sellableField = String.format("%s-sellable",skuId);
        String withholdingField = String.format("%s-withholding",skuId);
        String occupiedField = String.format("%s-occupied",skuId);
        Object[] args = {sellableField, withholdingField, occupiedField, sellableQuantity, withholdingQuantity, occupiedQuantity};
        return redisTemplate.execute(RedisScriptHolder.SCRIPT, Collections.singletonList(ORDER_INVENTORY_KEY), args);
    }

    /**
     * lua脚本Holder，采用懒加载机制
     */
    private static final class RedisScriptHolder{

        static final RedisScript<Boolean> SCRIPT;
        static final String SCRIPT_PATH = "inventory-change.script";

        static {
            ClassPathResource classPathResource = new ClassPathResource(SCRIPT_PATH);
            DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();
            defaultRedisScript.setLocation(classPathResource);
            SCRIPT = defaultRedisScript;
        }

    }


}
