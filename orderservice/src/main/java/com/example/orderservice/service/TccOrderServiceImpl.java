package com.example.orderservice.service;

import com.alibaba.nacos.api.config.annotation.NacosValue;
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

import java.util.Random;

/**
 * Created by maxb on 2019/11/22.
 */
public class TccOrderServiceImpl implements TccOrderService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger("tccorder");

    @NacosValue(value = "${order.probability}", autoRefreshed = true)
    private Integer orderProbability;


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
        if("fail".equals(orderDTO.getFlag())){
            Random random = new Random();
            int num = random.nextInt(100);
            if(num < orderProbability){
                logger.info("随机数："+num + "概率："+orderProbability);
                throw new RuntimeException("测试异常");
            }
        }
        if("kenan".equals(orderDTO.getFlag())){
            throw new RuntimeException("测试异常");
        }
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
