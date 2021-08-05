package com.jhmarryme.demo.web.service.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jhmarryme.demo.mapper.excel.CoderMapper;
import com.jhmarryme.demo.pojo.model.excel.Coder;
import com.jhmarryme.demo.pojo.model.excel.CoderExcel;
import com.jhmarryme.demo.pojo.model.excel.News;
import com.jhmarryme.demo.common.util.RedisUtil;
import com.jhmarryme.demo.pojo.vo.excel.DutyInfoRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 *
 * @author JiaHao Wang
 * @date 1/2/21 12:36 PM
 */
@Slf4j
@Service
public class DutyInfoService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CoderMapper coderMapper;

    public void printCoderExcelList(DutyInfoRequestVO requestVO, HttpServletResponse response) throws IOException {
        // 方法3 如果写到不同的sheet 不同的对象
        ExcelWriter excelWriter = null;

        LocalDate startDate = requestVO.getStartDate();
        LocalDate endDate = requestVO.getEndDate();
        if (startDate.isAfter(endDate)) {
            // 异常
            return;
        }
        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(response.getOutputStream(), CoderExcel.class).build();

            int index = requestVO.getStartOrder() != null ? requestVO.getStartOrder() - 1 : 0;
            int sheetNo = 0;
            while (startDate.isBefore(endDate)) {
                index = printCoderExcelByMonth(excelWriter, sheetNo, startDate, index);
                sheetNo++;
                startDate = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 根据参数, 将当月的排班信息输出到excel sheet页中
     *
     * @author Jiahao Wang
     * @date 2021/2/28 22:56
     * @param excelWriter excel流
     * @param sheetNo sheet号
     * @param date 每月的起始日期
     * @param index 起始的coder index
     * @return int
     */
    public int printCoderExcelByMonth(ExcelWriter excelWriter, int sheetNo, LocalDate date, int index) {

        LocalDate startDate = date;
        int currentMonth = startDate.getMonth().getValue();

        Coder coder = coderMapper.selectByPrimaryKey(1);

        List<Coder> coderList = coderMapper.selectAll();
        // 排序
        coderList.sort(Comparator.comparing(Coder::getRotationOrder));

        List<CoderExcel> coderExcelList = new ArrayList<>();

        String item = date.getYear() + "-" + (currentMonth < 10 ? "0" + currentMonth : currentMonth);

        // 获取redis数据
        List<News> newsList = (List<News>) redisUtil.hget(String.valueOf(date.getYear()), item);
        // 统计当月的所有日期信息
        while (startDate.getMonth().getValue() == currentMonth) {
            News news = getCurrentPerDateInfo(startDate, newsList).orElseThrow();
            // 只安排工作日
            if (news.getIsnotwork() == 0) {
                coderExcelList.add(CoderExcel.builder()
                        .date(Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                        .name(coderList.get(index).getRealName())
                        .whatDay(news.getCnweekday())
                        .remark(news.getTip())
                        .build()
                );
                if (++index == coderList.size()) {
                    index = 0;
                }
            }
            startDate = startDate.plusDays(1);
        }
        // 按月 输出到sheet页中
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, currentMonth + "月").head(CoderExcel.class).build();
        excelWriter.write(coderExcelList, writeSheet);
        return index;
    }

    /**
     * 初始化当年的数据, 免费api每天调用数据有限, 所有缓存至redis
     * 最多只能获取到当年的信息, 每年底api会更新
     *
     * @author Jiahao Wang
     * @date 2021/3/1 12:34
     * @param year 年份
     */
    public void initYears(int year) {
        String httpUrl = "http://api.tianapi.com/txapi/jiejiari/index";

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.firstDayOfNextYear());

        // 按月查询
        while (startDate.isBefore(endDate)) {
            // 需要补零
            String date = startDate.getYear() + "-" + (startDate.getMonth().getValue() < 10 ?
                    "0" + startDate.getMonth().getValue() : startDate.getMonth().getValue());
            String httpArg = "key=1c5835b88ebba7b674891d603c69e72f&date=" + date + "&type=2";
            String jsonResult = request(httpUrl, httpArg);

            List<News> news = handleJsonResult(jsonResult);

            // 按月 将信息存入redis
            redisUtil.hset(String.valueOf(year), date, news);
            startDate = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
            log.info("{}: \n result: {}", date, jsonResult);
            log.info("==============================================");
        }
    }

    /**
     * 根据年份 返回当年的的日期信息
     *
     * @author Jiahao Wang
     * @date 2021/2/28 22:53
     * @param year 年份
     */
    public List<News> getYearsData(int year) {
        List<News> newsList = new ArrayList<>();
        LocalDate startDate = LocalDate.of(year, 1, 1);
        // 将结束日期指向当年的最后一天
        LocalDate endDate = startDate.with(TemporalAdjusters.firstDayOfNextYear());
        while (startDate.isBefore(endDate)) {
            String date = startDate.getYear() + "-" + (startDate.getMonth().getValue() < 10 ? "0" + startDate.getMonth().getValue(): startDate.getMonth().getValue());
            List<News> hget = (List<News>) redisUtil.hget(String.valueOf(year), date);

            newsList.addAll(hget);
            log.info("{}的信息为:\n{}", year + "-" + startDate.getMonth().getValue(), hget);
            startDate = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
        }

        return newsList;
    }

    /**
     * 解析月度的信息
     *
     * @author Jiahao Wang
     * @date 2021/2/28 22:53
     * @param jsonResult api返回的结果
     * @return java.util.List<com.jhmarryme.demo.pojo.test.News>
     */
    private List<News> handleJsonResult(String jsonResult) {
        // newslist中的vacation/remark 字段为空时, 值为"", 非[]
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                //过滤指定的字段名
                return f.getName().contains("vacation") || f.getName().contains("remark");
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        JsonObject jsonObject = JsonParser.parseString(jsonResult).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("newslist").getAsJsonArray();
        return gson.fromJson(jsonArray.toString(), new TypeToken<List<News>>() {}.getType());
    }

    /**
     * 根据时间从月度信息中 取出当日的信息
     *
     * @author Jiahao Wang
     * @date 2021/2/28 22:51
     * @param localDate 日期
     * @param list 月度信息
     * @return java.util.Optional<com.jhmarryme.demo.pojo.test.News>
     */
    public Optional<News> getCurrentPerDateInfo(LocalDate localDate, List<News> list) {
        return list.stream().filter(news -> news.getDate().equals(localDate.toString())).findFirst();
    }

    /**
     * @param httpUrl
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
