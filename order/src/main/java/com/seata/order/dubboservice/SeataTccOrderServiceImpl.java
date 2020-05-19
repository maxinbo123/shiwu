package com.seata.order.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.OrderDubboService;
import com.seata.common.dubbo.SeatTccOrderService;
import com.seata.order.entity.TOrder;
import com.seata.order.mapper.TOrderMapper;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Random;
import java.util.UUID;

/**
 * Created by maxb on 2019/11/7.
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        group = "seataTccOrder",timeout = 3000)
public class SeataTccOrderServiceImpl implements SeatTccOrderService {

    @NacosValue(value = "${order.probability}", autoRefreshed = true)
    private Integer orderProbability;

    @Autowired
    private TransactionTemplate fromDsTransactionTemplate;

    @Autowired
    private TOrderMapper tOrderMapper;
    private Logger logger = LoggerFactory.getLogger("bussiness");

    @Override
    public ResponseData<OrderDTO> createOrder(BusinessActionContext businessActionContext, OrderDTO orderDTO) {
        //分布式事务id
        final String xid = RootContext.getXID();;
        logger.info("事务id={}-----， pre",xid);
        return fromDsTransactionTemplate.execute(new TransactionCallback<ResponseData>() {
            @Nullable
            @Override
            public ResponseData doInTransaction(TransactionStatus transactionStatus) {
                try{
                    TOrder tOrder = new TOrder();
                    BeanUtils.copyProperties(orderDTO,tOrder);
                    tOrderMapper.createOrder(tOrder);
                    return new ResponseData("200","success");
                }catch (Exception e){
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                    return new ResponseData("500","fail");
                }

            }
        });
    }

    @Override
    public ResponseData<OrderDTO> commit(BusinessActionContext businessActionContext) {
        final String xid = businessActionContext.getXid();
        logger.info("事务id={}-----， commit",xid);
        return new ResponseData("200","success");
    }

    @Override
    public ResponseData<OrderDTO> rollback(BusinessActionContext businessActionContext) {
        //分布式事务id
        final String xid = businessActionContext.getXid();
        logger.info("事务id={}-----， rollback",xid);
        return fromDsTransactionTemplate.execute(new TransactionCallback<ResponseData>() {
            @Nullable
            @Override
            public ResponseData doInTransaction(TransactionStatus transactionStatus) {
                try{
                    final OrderDTO orderDTO = (OrderDTO)businessActionContext.getActionContext("orderDTO");
                    //获取该行的锁
                    TOrder tOrder = new TOrder();
                    BeanUtils.copyProperties(orderDTO,tOrder);
                    tOrderMapper.deleteOrder(tOrder);
                    return new ResponseData("200","success");
                }catch (Exception e){
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                    return new ResponseData("500","fail");
                }

            }
        });
    }
}
