package com.example.orderservice.controller;

import com.example.commfacade.dto.CommodityDTO;
import com.example.commfacade.dto.OrderDTO;
import com.example.commfacade.service.TccOrderService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by maxb on 2019/11/20.
 */
@Controller
public class TestController {

    @Autowired
    private TccOrderService storageTccService;


    @ResponseBody
    @RequestMapping("/test")
    public String Test(){
        try{
            OrderDTO dto = new OrderDTO();
            dto.setTccId("tci12121");
            dto.setOrderNo("uidsd121312");
            dto.setCommodityCode("C2019");
            dto.setOrderAmount(new BigDecimal("5"));
            dto.setUserId("uid2121");
            storageTccService.createOrder(dto);

        }catch (Exception e){
            e.printStackTrace();
            return "异常了"+e;
        }
        return "成功了";
    }
}
