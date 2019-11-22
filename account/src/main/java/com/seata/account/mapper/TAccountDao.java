package com.seata.account.mapper;


import org.apache.ibatis.annotations.Param;

/**
 * 账户信息表(TAccount)表数据库访问层
 *
 * @author hexc
 * @since 2019-09-16 09:28:07
 */
public interface TAccountDao {

    int decreaseAccount(@Param("userId") String userId, @Param("amount") Double amount);

}