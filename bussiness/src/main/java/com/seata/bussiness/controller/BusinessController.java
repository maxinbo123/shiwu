package com.seata.bussiness.controller;

import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.alibaba.fastjson.JSON;
import com.seata.bussiness.service.BusinessService;
import com.seata.common.dto.BusinessDTO;
import com.seata.common.dto.ResponseData;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: heshouyou
 * @Description  Dubbo业务执行入口
 * @Date Created in 2019/1/14 17:15
 */
@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController{

    private Logger logger = LoggerFactory.getLogger("bussiness");

    @Autowired
    private BusinessService businessService;

    /**
     * 模拟用户购买商品下单业务逻辑流程seata
     * @Param:
     * @Return:
     */
    @RequestMapping("/buy")
    @ResponseBody
    ResponseData handleBusiness(HttpServletRequest request, HttpServletResponse response){
        String traceId = TraceIdUtil.getTraceId();
        Map<String,String> params = getParametersFromPage(request);
        long startTime = System.currentTimeMillis();
        logger.warn("入参={}，traceId={}",JSON.toJSONString(params),traceId);
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setUserId(params.get("userId"));
        businessDTO.setCount(Integer.valueOf(params.get("count")));
        businessDTO.setAmount(new BigDecimal(params.get("amount")));
        businessDTO.setName(params.get("name"));
        int index=(int)(Math.random()*50000) +1;
        businessDTO.setCommodityCode("C2019-"+index);
        ResponseData responseData = businessService.handleBusiness(businessDTO);
        logger.warn("整体结束 traceId={}, 耗时={}",traceId,System.currentTimeMillis()-startTime);
        return responseData ;
    }


    /**
     * 模拟用户购买商品下单业务逻辑流程seata
     * @Param:
     * @Return:
     */
    @RequestMapping("/tccSeata")
    @ResponseBody
    ResponseData tccSeataBusiness(HttpServletRequest request, HttpServletResponse response){
        String traceId = TraceIdUtil.getTraceId();
        Map<String,String> params = getParametersFromPage(request);
        long startTime = System.currentTimeMillis();
        logger.warn("入参={}，traceId={}",JSON.toJSONString(params),traceId);
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setUserId(params.get("userId"));
        businessDTO.setCount(Integer.valueOf(params.get("count")));
        businessDTO.setAmount(new BigDecimal(params.get("amount")));
        String flag = params.get("random");
        if("random".equals(flag)){
            int index=(int)(Math.random()*50000) +1;
            businessDTO.setCommodityCode("C2019-"+index);
        }else {
            businessDTO.setCommodityCode(params.get("commodityCode"));
        }
        businessDTO.setName(params.get("name"));
        ResponseData responseData = businessService.seataTccBusiness(businessDTO);
        logger.warn("整体结束 traceId={}, 耗时={}",traceId,System.currentTimeMillis()-startTime);
        return responseData ;
    }





    /**
     * 多线程下跑seata
     * @Param:
     * @Return:
     */
    @RequestMapping("/buyMore")
    @ResponseBody
    String moreBusiness(HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String,String> params = getParametersFromPage(request);
            Integer count = 10;
            if(!StringUtils.isEmpty(params.get("total"))){
                count = Integer.valueOf(params.get("total"));
            }
            logger.info("入参："+JSON.toJSONString(params));
            BusinessDTO businessDTO = new BusinessDTO();
            businessDTO.setUserId(params.get("userId"));
            businessDTO.setCommodityCode(params.get("commodityCode"));
            businessDTO.setCount(Integer.valueOf(params.get("count")));
            businessDTO.setAmount(new BigDecimal(params.get("amount")));
            businessDTO.setName(params.get("name"));
            ExecutorService executorService = Executors.newCachedThreadPool();
            for(int i = 0;i<count;i++){
                businessService.handleBusiness(businessDTO);
            }
            return "可能成功了";
        }catch (Exception e){
            e.printStackTrace();
            return "真是异常了";
        }
    }
}
