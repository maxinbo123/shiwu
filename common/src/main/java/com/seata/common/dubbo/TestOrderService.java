package com.seata.common.dubbo;


import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;

/**
 * @Author: heshouyou
 * @Description  订单服务接口
 * @Date Created in 2019/1/13 16:28
 */
public interface TestOrderService {

    /**
     * 创建订单
     */
    ResponseData<OrderDTO> createOrder(OrderDTO orderDTO);
}
