package com.example.storageservice.service;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.example.commfacade.dto.CommodityDTO;
import com.example.commfacade.dto.ResponseData;
import com.example.commfacade.service.TccStorageService;
import com.example.storageservice.mapper.TStorageMapper;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by maxb on 2019/11/22.
 */
public class TccStorageServiceImpl implements TccStorageService {
    private Logger logger = org.slf4j.LoggerFactory.getLogger("tccstorage");

    @NacosValue(value = "${storage.probability}", autoRefreshed = true)
    private Integer storageProbability;

    @Autowired
    private TStorageMapper tStorageMapper;

    @Compensable(confirmMethod = "confirmStorage", cancelMethod = "cancelStorage", transactionContextEditor = DubboTransactionContextEditor.class)
    @Override
    public ResponseData decreaseStorage(CommodityDTO commodityDTO) {
        int storage = tStorageMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount(),commodityDTO.getTccId());
        if("fail".equals(commodityDTO.getFlag())){
            Random random = new Random();
            int num = random.nextInt(100);
            if(num < storageProbability){
                logger.info("随机数："+num + "概率："+storageProbability);
                throw new RuntimeException("测试异常");
            }
        }
        if("lan".equals(commodityDTO.getFlag())){
            throw new RuntimeException("测试异常");
        }
        if (storage > 0){
            return new ResponseData("200","success");
        }else {
            return new ResponseData("500","fail");
        }

    }

    public void confirmStorage(CommodityDTO commodityDTO) throws Exception{
        logger.info("-----confirmStorage----");
    }

    public void cancelStorage(CommodityDTO commodityDTO) throws Exception{
        logger.info("-----cancelStorage----");
        tStorageMapper.addStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount(),commodityDTO.getTccId());
    }
}
