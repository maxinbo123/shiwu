package com.seata.bussiness.service;


import com.seata.common.dto.BusinessDTO;
import com.seata.common.dto.ResponseData;

/**
 * @Author: heshouyou
 * @Description
 * @Date Created in 2019/1/14 17:17
 */
public interface BusinessService {

    ResponseData handleBusiness(BusinessDTO businessDTO);

}
