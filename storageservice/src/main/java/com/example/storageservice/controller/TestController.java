package com.example.storageservice.controller;

import com.example.commfacade.dto.CommodityDTO;
import com.example.commfacade.service.TccStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maxb on 2019/11/20.
 */
@Controller
public class TestController {

    @Autowired
    private TccStorageService storageTccService;


    @ResponseBody

    @RequestMapping("/test")
    public String Test(){
        try{
            CommodityDTO commodityDTO = new CommodityDTO();
            commodityDTO.setTccId("tcI1212121212");
            commodityDTO.setCommodityCode("C2019");
            commodityDTO.setCount(5);
            storageTccService.decreaseStorage(commodityDTO);

        }catch (Exception e){
            return "异常了"+e;
        }
        return "成功了";
    }
}
