package com.seata.storage.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.seata.common.dto.CommodityDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.SeataTccStorageService;
import com.seata.common.dubbo.StorageDubboService;
import com.seata.storage.mapper.TStorageMapper;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Random;

/**
 * Created by maxb on 2019/11/7.
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        group="tccSeataStorage",timeout = 5000)
public class SeataTccStorageServiceImpl implements SeataTccStorageService {

    @NacosValue(value = "${storage.probability}", autoRefreshed = true)
    private Integer storageProbability;

    @Autowired
    private TransactionTemplate fromDsTransactionTemplate;

    @Autowired
    private TStorageMapper tStorageMapper;
    private Logger logger = LoggerFactory.getLogger("bussiness");

    @Override
    public ResponseData decreaseStorage(BusinessActionContext businessActionContext, CommodityDTO commodityDTO) {
        //分布式事务id
        final String xid = RootContext.getXID();
        logger.info("事务id={}-----， pre",xid);
        return fromDsTransactionTemplate.execute(new TransactionCallback<ResponseData>() {
            @Nullable
            @Override
            public ResponseData doInTransaction(TransactionStatus transactionStatus) {
                try{
                    //获取该行的锁
                 //   tStorageMapper.getStorageForUpdate(commodityDTO.getCommodityCode());
                    tStorageMapper.preDecreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
                    return new ResponseData("200","success");
                }catch (Exception e){
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                    return new ResponseData("500","fail");
                }

            }
        });
    }

    @Override
    public ResponseData commit(BusinessActionContext businessActionContext) {
        //分布式事务id
        final String xid = businessActionContext.getXid();
        logger.info("事务id={}-----， commit",xid);
        //huo
        final CommodityDTO commodityDTO = (CommodityDTO)businessActionContext.getActionContext("commodityDTO");
        return fromDsTransactionTemplate.execute(new TransactionCallback<ResponseData>() {
            @Nullable
            @Override
            public ResponseData doInTransaction(TransactionStatus transactionStatus) {
                try{
                    //获取该行的锁
                    tStorageMapper.getStorageForUpdate(commodityDTO.getCommodityCode());
                    tStorageMapper.trueDecStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
                    return new ResponseData("200","success");
                }catch (Exception e){
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                    return new ResponseData("500","fail");
                }

            }
        });
    }

    @Override
    public ResponseData rollback(BusinessActionContext businessActionContext) {
        //分布式事务id
        final String xid = businessActionContext.getXid();
        logger.info("事务id={}-----， rollback",xid);
        //huo
        final CommodityDTO commodityDTO = (CommodityDTO)businessActionContext.getActionContext("commodityDTO");
        return fromDsTransactionTemplate.execute(new TransactionCallback<ResponseData>() {
            @Nullable
            @Override
            public ResponseData doInTransaction(TransactionStatus transactionStatus) {
                try{
                    //获取该行的锁
                    tStorageMapper.getStorageForUpdate(commodityDTO.getCommodityCode());
                    tStorageMapper.rollbackStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
                    return new ResponseData("200","success");
                }catch (Exception e){
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                    return new ResponseData("500","fail");
                }

            }
        });
    }
}
