package com.example.commfacade.service;


import com.example.commfacade.dto.OrderDTO;
import com.example.commfacade.dto.ResponseData;
import org.mengyun.tcctransaction.api.Compensable;

/**
 * @Author: Tcc
 * @Description  订单服务接口
 * @Date Created in 2019/1/13 16:28
 */
public interface TccOrderService {

    /**
     * 创建订单
     */
    @Compensable
    ResponseData<OrderDTO> createOrder(OrderDTO orderDTO);
}
