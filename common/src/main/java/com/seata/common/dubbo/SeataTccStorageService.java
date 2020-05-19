package com.seata.common.dubbo;


import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.ResponseData;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 *
 * 库存减少
 */
public interface SeataTccStorageService {


    /**
     * 扣减库存
     */
    @TwoPhaseBusinessAction(name = "seataTccStorageService", commitMethod = "commit", rollbackMethod = "rollback")
    ResponseData decreaseStorage(BusinessActionContext businessActionContext,@BusinessActionContextParameter(paramName = "commodityDTO")CommodityDTO commodityDTO);




    /**
     * 扣减库存
     */
    ResponseData commit(BusinessActionContext businessActionContext);

    /**
     * 增加库存
     */
    ResponseData rollback(BusinessActionContext businessActionContext);
}
