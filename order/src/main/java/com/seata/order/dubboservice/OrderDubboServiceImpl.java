package com.seata.order.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.OrderDubboService;
import com.seata.order.entity.TOrder;
import com.seata.order.mapper.TOrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;

/**
 * Created by maxb on 2019/11/7.
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",timeout = 3000)
public class OrderDubboServiceImpl implements OrderDubboService {


    @Autowired
    private TOrderMapper tOrderMapper;

    @Override
    public ResponseData<OrderDTO> createOrder(OrderDTO orderDTO) {
        //生成订单号
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        //生成订单
        TOrder tOrder = new TOrder();
        BeanUtils.copyProperties(orderDTO,tOrder);
        tOrder.setCount(orderDTO.getOrderCount());
        tOrder.setAmount(orderDTO.getOrderAmount().doubleValue());
        tOrderMapper.createOrder(tOrder);
        if("fail".equals(orderDTO.getFlag())){
            Random random = new Random();
            int num = random.nextInt(100);
            if(num < 3){
                throw new RuntimeException("测试异常");
            }
        }

        return new ResponseData<OrderDTO>("200","success",orderDTO);
    }

    public static void main(String[] args) {
        Random random = new Random();
        for(int i=0; i<10; i++){
            System.out.print(random.nextInt(100) + "  ");
        }
    }
}
