package com.seata.storage.contorller;

import com.seata.storage.entity.TStorage;
import com.seata.storage.mapper.TStorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maxb on 2020/3/17.
 */
@RestController
public class InsertInfo {

    @Autowired
    private TStorageMapper tStorageMapper;

    @RequestMapping("/insert")
    @ResponseBody
    public String insert(){
        try{
            TStorage storage = new TStorage();
            storage.setCount(100);
            storage.setName("笔记本");
            for(int i = 1 ; i<=50001; i++){
                storage.setId(i);
                storage.setCommodityCode("C2019-"+i);
                tStorageMapper.insertStorage(storage);
            }

            return "success";
        }catch (Exception e){
           e.printStackTrace();;
           return "异常";
        }

    }
}
