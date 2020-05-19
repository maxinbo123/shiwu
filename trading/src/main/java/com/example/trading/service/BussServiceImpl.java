package com.example.trading.service;

import com.example.commfacade.dto.BusinessDTO;
import com.example.commfacade.dto.CommodityDTO;
import com.example.commfacade.dto.OrderDTO;
import com.example.commfacade.dto.ResponseData;
import com.example.commfacade.service.TccOrderService;
import com.example.commfacade.service.TccStorageService;
import com.example.trading.entity.TOrder;
import com.example.trading.mapper.TOrderMapper;
import com.example.trading.mapper.TStorageMapper;
import org.mengyun.tcctransaction.api.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by maxb on 2019/11/22.
 */
@Service
public class BussServiceImpl implements BussService {

    private Logger logger = LoggerFactory.getLogger("bussiness");

    @Autowired
    private TccOrderService tccOrderService;

    @Autowired
    private TccStorageService tccStorageService;

    @Autowired
    private TOrderMapper tOrderMapper;

    @Autowired
    private TStorageMapper tStorageMapper;
    @Override
    @Transactional
    public ResponseData Business(BusinessDTO businessDTO) {
        logger.info("本地事务");
        long startTime = System.currentTimeMillis();
        //1、扣减库存
        int storageCount = tStorageMapper.decreaseStorage(businessDTO.getCommodityCode(),businessDTO.getCount(),"");
        if (storageCount == 0) {
            throw new RuntimeException("异常了~~~");
        }
        //2、创建订单
        TOrder orderDTO = new TOrder();
        String orderNo = "oi"+UUID.randomUUID().toString().replace("-", "").toLowerCase();
        orderDTO.setOrderNo(orderNo);
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setCount(businessDTO.getCount());
        orderDTO.setAmount(businessDTO.getAmount());
        tOrderMapper.createOrder(orderDTO);
        if("kenan".equals(businessDTO.getName())){
            throw new RuntimeException("异常了~~~");
        }
        ResponseData objectResponse = new ResponseData("10000","成功");
        logger.info("本地事务消耗时间："+ (System.currentTimeMillis()-startTime));
        return objectResponse;
    }


    @Compensable(confirmMethod = "tccBusinessConfirm", cancelMethod = "tccBusinessCancel", asyncConfirm = false, asyncCancel = true)
    @Override
    public ResponseData tccBusiness(BusinessDTO businessDTO) {
        String tccId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        logger.info("tcc测试啦  tccId"+  tccId);
        long startTime = System.currentTimeMillis();
        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
            commodityDTO.setTccId(tccId);
            commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
            commodityDTO.setCount(businessDTO.getCount());
        commodityDTO.setFlag(businessDTO.getName());
        ResponseData storageResponse = tccStorageService.decreaseStorage(commodityDTO);
        if (!"200".equals(storageResponse.getCode())) {
        throw new RuntimeException();
    }


        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        String orderNo = "oi"+UUID.randomUUID().toString().replace("-", "").toLowerCase();
        orderDTO.setOrderNo(orderNo);
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        orderDTO.setTccId(tccId);
        orderDTO.setFlag(businessDTO.getName());
        ResponseData response = tccOrderService.createOrder(orderDTO);
        ResponseData objectResponse = new ResponseData("10000","成功");
        if("yu".equals(businessDTO.getName())){
            throw new RuntimeException("测试异常~~~");
        }
        logger.info("tcc事务消耗时间："+ (System.currentTimeMillis()-startTime));
        return objectResponse;
    }
    public void tccBusinessConfirm(BusinessDTO businessDTO){
        logger.info("tcc测试啦 confirm"  );
    }

    public void tccBusinessCancel(BusinessDTO businessDTO){
        logger.info("tcc测试啦 Cancel"  );
    }
}

