package com.example.orderservice.service;

import com.example.commfacade.service.TestService;

/**
 * Created by maxb on 2019/11/22.
 */
public class TestServiceImpl implements TestService {

    @Override
    public String createOrder() {
        return "dubbo service";
    }
}
