package com.seata.order.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.OrderDubboService;
import com.seata.order.entity.TOrder;
import com.seata.order.mapper.TOrderMapper;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

/**
 * Created by maxb on 2019/11/7.
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",timeout = 3000)
public class OrderDubboServiceImpl implements OrderDubboService {

    @NacosValue(value = "${order.probability}", autoRefreshed = true)
    private Integer orderProbability;

    @Autowired
    private TOrderMapper tOrderMapper;
    private Logger logger = LoggerFactory.getLogger("bussiness");
    @Override
    public ResponseData<OrderDTO> createOrder(OrderDTO orderDTO) {
        String traceId = TraceIdUtil.getTraceId();
        long startTime = System.currentTimeMillis();
        logger.warn("seata order开始全局事务，XID ={},traceId={}", RootContext.getXID(),traceId);
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
            if(num < orderProbability){
                logger.info("随机数："+num + "概率："+orderProbability);
                throw new RuntimeException("测试异常");
            }
        }
       /* count ++;
        if(count%2==0){
            throw new RuntimeException("测试异常2");
        }*/
        logger.warn("seata order 事务，XID ={},traceId={}，耗时={}", RootContext.getXID(),traceId,System.currentTimeMillis()-startTime);
        return new ResponseData<OrderDTO>("200","success",orderDTO);
    }

    public static void main(String[] args) {
        Random random = new Random();
        for(int i=0; i<10; i++){
            System.out.print(random.nextInt(100) + "  ");
        }
    }
}
