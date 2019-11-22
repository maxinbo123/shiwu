package com.example.trading.service;

import com.example.commfacade.dto.BusinessDTO;
import com.example.commfacade.dto.ResponseData;

/**
 * Created by maxb on 2019/11/22.
 */
public interface BussService {

    ResponseData tccBusiness(BusinessDTO businessDTO);

    ResponseData Business(BusinessDTO businessDTO);
}
