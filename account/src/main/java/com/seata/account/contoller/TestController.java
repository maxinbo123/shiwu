package com.seata.account.contoller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maxb on 2019/11/11.
 */
@RestController
public class TestController {

    @NacosValue(value = "${account.datasource.url}", autoRefreshed = true)
    private String testProperties;

    @GetMapping("/test")
    public String test(){
        return testProperties;
    }
}
