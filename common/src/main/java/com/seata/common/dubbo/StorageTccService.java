package com.seata.common.dubbo;


import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.ResponseData;
import org.mengyun.tcctransaction.api.Compensable;

/**
 * @Author: heshouyou
 * @Description  仓库服务
 * @Date Created in 2019/1/13 16:22
 */

public interface StorageTccService {

    /**
     * 扣减库存
     */
    @Compensable
    ResponseData decreaseStorage(CommodityDTO commodityDTO);
}
