package com.seata.bussiness.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.seata.bussiness.exception.SeataException;
import com.seata.common.dto.BusinessDTO;
import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.OrderDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.AccountDubboService;
import com.seata.common.dubbo.OrderDubboService;
import com.seata.common.dubbo.StorageDubboService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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


    /**
     * 处理业务逻辑  seata
     * @Param:
     * @Return:
     */
    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-gts-seata-example")
    public ResponseData handleBusiness(BusinessDTO businessDTO) {
        logger.info("seata开始全局事务，XID ="+  RootContext.getXID());
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
        if (!"200".equals(storageResponse.getCode())) {
            throw new RuntimeException("测试啦");
        }
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        orderDTO.setFlag(businessDTO.getName());
        ResponseData response = orderDubboService.createOrder(orderDTO);
        if("kenan".equals(businessDTO.getName())){
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
        return objectResponse;
    }
}
