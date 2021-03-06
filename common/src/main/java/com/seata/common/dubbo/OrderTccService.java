package com.seata.common.dubbo;


import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import org.mengyun.tcctransaction.api.Compensable;

/**
 * @Author: Tcc
 * @Description  订单服务接口
 * @Date Created in 2019/1/13 16:28
 */
public interface OrderTccService {

    /**
     * 创建订单
     */
    @Compensable
    ResponseData<OrderDTO> createOrder(OrderDTO orderDTO);
}
