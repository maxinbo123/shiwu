package com.seata.storage.mapper;


import com.seata.storage.entity.TStorage;
import org.apache.ibatis.annotations.Param;


public interface TStorageMapper {

    /**
     * 扣减商品库存
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int decreaseStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);


    int insertStorage(@Param("storage") TStorage storage);


    //锁数据
    int getStorageForUpdate(@Param("commodityCode") String commodityCode);


    /**
     * 预扣减商品库存
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int preDecreaseStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);


    int rollbackStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);
    /***
     * 真实扣减库存
     * @param count
     * @return
     */
    int trueDecStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);
}
