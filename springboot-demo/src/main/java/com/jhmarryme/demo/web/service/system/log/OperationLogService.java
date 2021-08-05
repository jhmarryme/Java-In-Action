package com.jhmarryme.demo.web.service.system.log;

import com.jhmarryme.demo.mapper.system.log.OperationLogMapper;
import com.jhmarryme.demo.pojo.model.system.log.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/2 10:37
 */
@Service
public class OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;


    public void insertOne(OperationLog operlog) {
        operationLogMapper.insert(operlog);
    }
}
