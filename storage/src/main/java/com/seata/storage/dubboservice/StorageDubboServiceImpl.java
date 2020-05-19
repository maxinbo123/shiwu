package com.seata.storage.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.StorageDubboService;
import com.seata.storage.mapper.TStorageMapper;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by maxb on 2019/11/7.
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        timeout = 5000)
public class StorageDubboServiceImpl implements StorageDubboService {

    @NacosValue(value = "${storage.probability}", autoRefreshed = true)
    private Integer storageProbability;

    @Autowired
    private TStorageMapper tStorageMapper;
    private Logger logger = LoggerFactory.getLogger("bussiness");
    @Override
    public ResponseData decreaseStorage(CommodityDTO commodityDTO) {
        String traceId = TraceIdUtil.getTraceId();
        long startTime = System.currentTimeMillis();
        logger.warn("seata-storage分支事务，XID ={},traceId={}",RootContext.getXID(),traceId);
        int storage = tStorageMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        if("fail".equals(commodityDTO.getFlag())){
            Random random = new Random();
            int num = random.nextInt(100);
            if(num < storageProbability){
                logger.info("随机数："+num + "概率："+storageProbability);
                throw new RuntimeException("测试异常");
            }
        }
        logger.warn("seata-storage分支事务，XID ={},traceId={},耗时={}", RootContext.getXID(),traceId,(System.currentTimeMillis()-startTime));
        if (storage > 0){
            return new ResponseData("200","success");
        }
        return new ResponseData("500","fail");
    }
}
