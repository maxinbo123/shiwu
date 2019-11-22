package com.seata.bussiness.controller;

import com.alibaba.fastjson.JSON;
import com.seata.bussiness.service.BusinessService;
import com.seata.common.dto.BusinessDTO;
import com.seata.common.dto.ResponseData;
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
    private BusinessService businessService;

    /**
     * 模拟用户购买商品下单业务逻辑流程seata
     * @Param:
     * @Return:
     */
    @RequestMapping("/buy")
    @ResponseBody
    ResponseData handleBusiness(HttpServletRequest request, HttpServletResponse response){
        Map<String,String> params = getParametersFromPage(request);
        logger.info("入参："+JSON.toJSONString(params));
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setUserId(params.get("userId"));
        businessDTO.setCommodityCode(params.get("commodityCode"));
        businessDTO.setCount(Integer.valueOf(params.get("count")));
        businessDTO.setAmount(new BigDecimal(params.get("amount")));
        businessDTO.setName(params.get("name"));
       return businessService.handleBusiness(businessDTO);
    }
}
