package com.example.trading.controller;

import com.alibaba.fastjson.JSON;
import com.example.commfacade.dto.BusinessDTO;
import com.example.commfacade.dto.ResponseData;
import com.example.trading.service.BussService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

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
    private BussService BussService;
    /**
     * 模拟用户购买商品下单业务逻辑流程  tcc
     * @Param:
     * @Return:
     */
    @RequestMapping("/tccbuy")
    @ResponseBody
    ResponseData buss(HttpServletRequest request, HttpServletResponse response){
        Map<String,String> params = getParametersFromPage(request);
        logger.info("入参："+ JSON.toJSONString(params));
        String flag = params.get("random");
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setUserId(params.get("userId"));
        if("random".equals(flag)){
            int index=(int)(Math.random()*50000) +1;
            businessDTO.setCommodityCode("C2019-"+index);
        }else {
            businessDTO.setCommodityCode(params.get("commodityCode"));
        }
        businessDTO.setCount(Integer.valueOf(params.get("count")));
        businessDTO.setAmount(new BigDecimal(params.get("amount")));
        businessDTO.setName(params.get("name"));
        return BussService.tccBusiness(businessDTO);
    }

    /**
     * 模拟用户购买商品下单业务逻辑流程  local
     * @Param:
     * @Return:
     */
    @RequestMapping("/localbuy")
    @ResponseBody
    ResponseData localbuy(HttpServletRequest request, HttpServletResponse response){
        Map<String,String> params = getParametersFromPage(request);
        logger.info("入参："+ JSON.toJSONString(params));
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setUserId(params.get("userId"));
        String flag = params.get("random");
        if("random".equals(flag)){
            int index=(int)(Math.random()*50000) +1;
            businessDTO.setCommodityCode("C2019-"+index);
        }else {
            businessDTO.setCommodityCode(params.get("commodityCode"));
        }
        businessDTO.setCount(Integer.valueOf(params.get("count")));
        businessDTO.setAmount(new BigDecimal(params.get("amount")));
        businessDTO.setName(params.get("name"));
        return BussService.Business(businessDTO);
    }


}
