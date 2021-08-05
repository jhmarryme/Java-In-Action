package com.jhmarryme.demo.web.service.system.log;

import com.jhmarryme.demo.mapper.system.log.ExceptionLogMapper;
import com.jhmarryme.demo.pojo.model.system.log.ExceptionLog;
import com.jhmarryme.demo.pojo.model.system.log.OperationLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/2 10:37
 */
@Service
public class ExceptionLogService {

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    public void insertOne(ExceptionLog exceptionLog) {
        exceptionLogMapper.insert(exceptionLog);
    }
}
