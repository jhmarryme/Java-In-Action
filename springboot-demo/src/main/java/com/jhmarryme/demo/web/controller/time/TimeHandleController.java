package com.jhmarryme.demo.web.controller.time;

import com.jhmarryme.demo.common.annotation.ResponseResultBody;
import com.jhmarryme.demo.mapper.time.TimeHandleMapper;
import com.jhmarryme.demo.pojo.model.time.ZoneTime;
import com.jhmarryme.demo.pojo.vo.time.TimeHandleReqVo;
import com.jhmarryme.demo.pojo.vo.time.TimeReceiveReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author JiaHao Wang
 * @date 2021/6/21 10:29
 */
@RestController
@RequestMapping("time")
public class TimeHandleController {

    @Autowired
    private TimeHandleMapper timeHandleMapper;

    @RequestMapping(path = "/handle", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseResultBody
    public ZoneTime timeHandle(@RequestBody TimeHandleReqVo timeHandleReqVo, @RequestParam String time) {

        ZoneTime zoneTime = ZoneTime.builder()
                .createdAt(Date.from(timeHandleReqVo.getCreatedAtInLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()))
                .updatedAt(timeHandleReqVo.getUpdatedAt())
                .build();
        timeHandleMapper.insert(zoneTime);

        ZoneTime zoneTimeInDb = timeHandleMapper.selectByPrimaryKey(zoneTime.getId());

        return zoneTimeInDb;
    }

    /**
     * 接收 多种日期类型
     *
     * @author Jiahao Wang
     * @date 2021/6/21 13:55
     * @param timeReceiveReqVO timeReceiveReqVO
     * @param date date
     * @return java.lang.String
     */
    @RequestMapping(path = "/receive", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseResultBody
    public String test(@RequestBody TimeReceiveReqVO timeReceiveReqVO, @RequestParam("date") @DateTimeFormat(pattern
            = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        return "成功";
    }

}
