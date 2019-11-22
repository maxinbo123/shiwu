package com.seata.common.dubbo;

import com.seata.common.dto.AccountDTO;
import com.seata.common.dto.ResponseData;

/**
 * @Author: heshouyou
 * @Description  账户服务接口
 * @Date Created in 2019/1/13 16:37
 */
public interface AccountDubboService {

    /**
     * 从账户扣钱
     */
    ResponseData decreaseAccount(AccountDTO accountDTO);
}
