package com.seata.bussiness.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.seata.bussiness.exception.SeataException;
import com.seata.common.dto.BusinessDTO;
import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.*;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: heshouyou
 * @Description  Dubbo业务发起方逻辑
 * @Date Created in 2019/1/14 18:36
 */
@Service
public class BusinessServiceImpl implements BusinessService{
    private Logger logger = LoggerFactory.getLogger("bussiness");
    @Reference(version = "5.0")
    private StorageDubboService storageDubboService;

    @Reference(version = "5.0")
    private OrderDubboService orderDubboService;
  // @Reference(version = "5.0")
    private AccountDubboService accountDubboService;

    @Reference(version = "5.0",group = "tccSeataStorage")
    private SeataTccStorageService seataTccStorageService;

    @Reference(version = "5.0",group = "seataTccOrder")
    private SeatTccOrderService seatTccOrderService;
    /**
     * 处理业务逻辑  seata
     * @Param:
     * @Return:
     */
    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-gts-seata-example")
    //@GlobalTransactional(timeoutMills = 300000)
    public ResponseData handleBusiness(BusinessDTO businessDTO) {
        String traceId = TraceIdUtil.getTraceId();
        logger.warn("seata开始全局事务，XID={},traceId={}", RootContext.getXID(),traceId );
        long startTime = System.currentTimeMillis();
        ResponseData objectResponse = new ResponseData("10000","成功");
       /* //1、扣用户余额
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(businessDTO.getUserId());
        accountDTO.setAmount(businessDTO.getAmount());
        ResponseData accountResponse = accountDubboService.decreaseAccount(accountDTO);*/

        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        commodityDTO.setFlag(businessDTO.getName());
        ResponseData storageResponse = storageDubboService.decreaseStorage(commodityDTO);
        logger.warn("seata 事务 减库存耗时={},traceId={}",(System.currentTimeMillis()-startTime),traceId);
        if (!"200".equals(storageResponse.getCode())) {
            throw new RuntimeException("测试啦");
        }
        long orderTime = System.currentTimeMillis();
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        orderDTO.setFlag(businessDTO.getName());
        ResponseData response = orderDubboService.createOrder(orderDTO);
        logger.warn("seata 事务 订单耗时={},traceId={}",(System.currentTimeMillis()-orderTime),traceId);
        if("lan".equals(businessDTO.getName())){
         /*   try{
                Thread.sleep(1000*3);
            }catch (Exception e){

            }
*/
            throw new SeataException("测试异常");
        }
        if (!"200".equals(response.getCode())) {
            throw new RuntimeException("seata异常");
        }
        logger.warn("seata 总共事务耗时={},traceId={}",(System.currentTimeMillis()-startTime),traceId);
        return objectResponse;
    }

    @Override
    @GlobalTransactional
    public ResponseData seataTccBusiness(BusinessDTO businessDTO) {
        ResponseData objectResponse = new ResponseData("10000","成功");
        String traceId = TraceIdUtil.getTraceId();
        logger.warn("seata开始全局事务，XID={},traceId={}", RootContext.getXID(),traceId );
        long startTime = System.currentTimeMillis();
        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        commodityDTO.setFlag(businessDTO.getName());
        ResponseData storageResponse = seataTccStorageService.decreaseStorage(null,commodityDTO);
        logger.warn("seata 事务 减库存耗时={},traceId={}",(System.currentTimeMillis()-startTime),traceId);
        if (!"200".equals(storageResponse.getCode())) {
            throw new RuntimeException("测试啦");
        }
        long orderTime = System.currentTimeMillis();
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        orderDTO.setFlag(businessDTO.getName());
        ResponseData response = seatTccOrderService.createOrder(null,orderDTO);
        logger.warn("seata 事务 订单耗时={},traceId={}",(System.currentTimeMillis()-orderTime),traceId);
        if("lan".equals(businessDTO.getName())){
         /*   try{
                Thread.sleep(1000*3);
            }catch (Exception e){

            }
*/
            throw new SeataException("测试异常");
        }
        if (!"200".equals(response.getCode())) {
            throw new RuntimeException("seata异常");
        }
        logger.warn("seata 总共事务耗时={},traceId={}",(System.currentTimeMillis()-startTime),traceId);
        return objectResponse;
    }


    public SeataTccStorageService getSeataTccStorageService() {
        return seataTccStorageService;
    }

    public void setSeataTccStorageService(SeataTccStorageService seataTccStorageService) {
        this.seataTccStorageService = seataTccStorageService;
    }

    public SeatTccOrderService getSeatTccOrderService() {
        return seatTccOrderService;
    }

    public void setSeatTccOrderService(SeatTccOrderService seatTccOrderService) {
        this.seatTccOrderService = seatTccOrderService;
    }
}
