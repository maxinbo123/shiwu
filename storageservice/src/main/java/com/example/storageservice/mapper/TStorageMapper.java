package com.example.storageservice.mapper;


import org.apache.ibatis.annotations.Param;


public interface TStorageMapper {

    /**
     * 扣减商品库存
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int decreaseStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count, @Param("tccId") String tccId);

    int addStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count, @Param("tccId") String tccId);
}
