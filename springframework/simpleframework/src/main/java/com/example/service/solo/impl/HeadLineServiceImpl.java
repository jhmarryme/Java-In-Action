package com.example.service.solo.impl;


import com.example.entity.bo.HeadLine;
import com.example.entity.dto.Result;
import com.example.service.solo.HeadLineService;
import org.springframework.core.annotation.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        System.out.println("【INFO】addHeadLine被执行啦, lineName[" + headLine.getLineName() +
                "],lineLink[" + headLine.getLineLink() + "],lineImg[" + headLine.getLineImg() + "], priority[" + headLine.getPriority() + "]");
        Result<Boolean> result = new Result<Boolean>();
        result.setCode(200);
        result.setMsg("请求成功啦");
        result.setData(true);
        return result;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize) {
        return null;
    }
}
