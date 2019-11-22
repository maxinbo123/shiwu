package com.seata.storage.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.StorageDubboService;
import com.seata.storage.mapper.TStorageMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by maxb on 2019/11/7.
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        timeout = 3000)
public class StorageDubboServiceImpl implements StorageDubboService {

    @Autowired
    private TStorageMapper tStorageMapper;

    @Override
    public ResponseData decreaseStorage(CommodityDTO commodityDTO) {
        int storage = tStorageMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        if("fail".equals(commodityDTO.getFlag())){
            Random random = new Random();
            int num = random.nextInt(100);
            if(num < 4){
                throw new RuntimeException("测试异常");
            }
        }
        if (storage > 0){
            return new ResponseData("200","success");
        }
        return new ResponseData("500","fail");
    }
}
