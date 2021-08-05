package com.jhmarryme.demo.web.controller.excel;

import com.jhmarryme.demo.pojo.model.excel.CoderExcel;
import com.jhmarryme.demo.pojo.model.excel.News;
import com.jhmarryme.demo.pojo.vo.excel.DutyInfoRequestVO;
import com.jhmarryme.demo.web.service.excel.DutyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author JiaHao Wang
 * @date 1/2/21 12:28 PM
 */
@RestController
@RequestMapping("duty")
public class DutyInfoController {

    @Autowired
    private DutyInfoService dutyInfoService;

    /**
     * 初始化当年的数据, 免费api每天调用数据有限, 所有缓存至redis
     * 最多只能获取到当年的信息, 每年底api会更新
     *
     * @author Jiahao Wang
     * @date 2021/2/28 22:47
     * @param year 年份
     * @return void
     */
    @GetMapping("init")
    public void initDutyInfo(int year) {
        dutyInfoService.initYears(year);
    }

    /**
     * 获取当年的信息
     *
     * @author Jiahao Wang
     * @date 2021/3/1 12:34
     * @param year
     * @return void
     * @throws
     */
    @GetMapping("get")
    public List<News> getDutyInfo(int year) {
        return dutyInfoService.getYearsData(year);
    }

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>1. 创建excel对应的实体对象 参照{@link CoderExcel}
     * <p>2. 设置返回的 参数
     * <p>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @RequestMapping("download")
    public void download(@RequestBody DutyInfoRequestVO requestVO, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("每日轮值", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        dutyInfoService.printCoderExcelList(requestVO, response);
    }

}
