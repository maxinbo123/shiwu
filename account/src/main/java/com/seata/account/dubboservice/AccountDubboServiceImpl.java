package com.seata.account.dubboservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.seata.account.mapper.TAccountDao;
import com.seata.common.dto.AccountDTO;
import com.seata.common.dto.ResponseData;
import com.seata.common.dubbo.AccountDubboService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 账户服务接口
 * Created by maxb on 2019/11/7.
 *
 *
 */
@Service(version = "5.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        timeout = 3000)
public class AccountDubboServiceImpl implements AccountDubboService {

    @Autowired
    private TAccountDao tAccountDao;

    /**
     * 从账户扣钱
     */
    @Override
    public ResponseData decreaseAccount(AccountDTO accountDTO) {
        System.out.print("全局事务id：" + RootContext.getXID());
        int account = tAccountDao.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        if (account > 0) {
            return new ResponseData("10000", "success");
        }
        return new ResponseData("20000", "fail");
    }
}
