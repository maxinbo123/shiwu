package com.seata.common.dubbo;


import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Author: heshouyou
 * @Description  订单服务接口
 * @Date Created in 2019/1/13 16:28
 */
public interface SeatTccOrderService {

    /**
     * 创建订单
     */
    @TwoPhaseBusinessAction(name = "seatTccOrderService", commitMethod = "commit", rollbackMethod = "rollback")
    ResponseData<OrderDTO> createOrder(BusinessActionContext businessActionContext, @BusinessActionContextParameter(paramName = "orderDTO")OrderDTO orderDTO);


    /**
     * 创建订单
     */
    ResponseData<OrderDTO> commit(BusinessActionContext businessActionContext);



    /**
     * 删除订单
     */
    ResponseData<OrderDTO> rollback(BusinessActionContext businessActionContext);

}
