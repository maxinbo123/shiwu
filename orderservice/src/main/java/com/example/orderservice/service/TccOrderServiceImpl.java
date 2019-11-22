package com.example.orderservice.service;

import com.example.commfacade.dto.OrderDTO;
import com.example.commfacade.dto.ResponseData;
import com.example.commfacade.service.TccOrderService;
import com.example.orderservice.entity.TOrder;
import com.example.orderservice.mapper.TOrderMapper;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maxb on 2019/11/22.
 */
public class TccOrderServiceImpl implements TccOrderService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger("tccorder");

    @Autowired
    private TOrderMapper tOrderMapper;

    @Compensable(confirmMethod = "confirmOrder", cancelMethod = "cancelOrder", transactionContextEditor = DubboTransactionContextEditor.class)
    @Override
    public ResponseData<OrderDTO> createOrder(OrderDTO orderDTO) {
        //生成订单
        TOrder tOrder = new TOrder();
        BeanUtils.copyProperties(orderDTO,tOrder);
        tOrder.setCount(orderDTO.getOrderCount());
        tOrder.setAmount(orderDTO.getOrderAmount().doubleValue());
        tOrderMapper.createOrder(tOrder);
        return new ResponseData<OrderDTO>("200","success",orderDTO);
    }

    public void confirmOrder(OrderDTO orderDTO) throws Exception{
        System.out.println("-----confirmOrder----");
        logger.info("-----confirmOrder----");
    }

    public void cancelOrder(OrderDTO orderDTO) throws Exception{
        logger.info("-----cancelOrder----");
        System.out.println("-----cancelOrder----");
        tOrderMapper.deleteOrder(orderDTO.getOrderNo());

    }
}
