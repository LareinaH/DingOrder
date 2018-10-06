package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.mapper.StatMapper;
import com.cotton.abmallback.model.excel.ShipInOut;
import com.cotton.base.common.RestResponse;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * AdminLoginController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/29
 */
@RestController
@RequestMapping(value = "/admin/stat", produces = "application/json; charset=UTF-8")
public class StatController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StatMapper statMapper;

    @RequestMapping(value = "/getOrderStatusStats", method = {RequestMethod.GET})
    public RestResponse<List<Map<String, Long>>> get(@RequestParam(value = "gmtStart") String gmtStart, @RequestParam(value = "gmtEnd") String gmtEnd) {
        List<Map<String, Long>> listMap = statMapper.getOrderStatusStats(gmtStart, gmtEnd);
        return RestResponse.getSuccesseResponse(listMap);
    }


    @RequestMapping(value = "/memberStat", method = {RequestMethod.GET})
    public RestResponse<Map<String, Object>> memberStat() {
        Map<String, Object> resultMap = new HashMap<>(16);

        Date lastDayBegin = getLastDayBegin();
        Date lastDayEnd = getLastDayEnd();
        Date lastMonthBegin = getLastMonthBegin();
        Date lastMonthEnd = getLastMonthEnd();

        //平台用户量
        long memberTotalCount = statMapper.getTotalMember();
        resultMap.put("userTotalCount", memberTotalCount);

        //会员注册量
        long agentMemberCount = statMapper.getAgentMemberCount();
        resultMap.put("agentMemberCount", agentMemberCount);

        //昨日会员注册量
        long memberTotalCountLastDay = statMapper.getAgentMemberCountByTime(lastDayBegin, lastDayEnd);
        resultMap.put("agentMemberTotalCountLastDay", memberTotalCountLastDay);

        //上月会员注册量
        long memberTotalCountLastMonth = statMapper.getAgentMemberCountByTime(lastMonthBegin, lastMonthEnd);
        resultMap.put("agentMemberTotalCountLastMonth", memberTotalCountLastMonth);

        //总订单量
        long ordersTotalCount = statMapper.getTotalMember();
        resultMap.put("ordersTotalCount", ordersTotalCount);

        //昨日会员订单数
        long ordersTotalCountLastDay = statMapper.getOrdersCountByTime(lastDayBegin, lastDayEnd);
        resultMap.put("ordersTotalCountLastDay", ordersTotalCountLastDay);

        //上月会员订单数
        long ordersTotalCountLastMonth = statMapper.getOrdersCountByTime(lastDayBegin, lastDayEnd);
        resultMap.put("ordersTotalCountLastMonth", ordersTotalCountLastMonth);

        //总购物额度
        BigDecimal orderMoney = statMapper.getOrderMoney();
        //累积会员人均订单数=累积至昨日会员订单总数/累积至昨日会员总数
        resultMap.put("memberOrdersCountAvg", String.format("%.2f", (double) ordersTotalCount / agentMemberCount));

        //累积会员人均购物额=累积至昨日会员购物总额/累积至昨日会员总数
        resultMap.put("memberOrdersMoneyAvg", String.format("%.2f", orderMoney.divide(BigDecimal.valueOf(agentMemberCount).multiply(BigDecimal.valueOf(100)), 2, BigDecimal.ROUND_HALF_EVEN)));


        //复购率=累积至昨日复够用户数/累积至昨日用户数
        long repurchaseMemberCount = statMapper.getRepurchaseMemberCount();
        resultMap.put("repurchasePercent", String.format("%.2f", (double) repurchaseMemberCount / memberTotalCount));

        //上月复购率
        long repurchaseMemberCountLastMonth = statMapper.getRepurchaseMemberCountByTime(lastMonthBegin, lastMonthEnd);
        if (memberTotalCountLastMonth > 0) {
            resultMap.put("repurchasePercentLastMonth", String.format("%.2f", (double) repurchaseMemberCountLastMonth / memberTotalCountLastMonth));
        } else {
            resultMap.put("repurchasePercentLastMonth", 0);

        }

        //用户购买率=累积至昨日有效订单用户数/累积至昨日用户总数
        resultMap.put("memberBuyPercent", String.format("%.2f", (double) agentMemberCount / memberTotalCount));

        return RestResponse.getSuccesseResponse(resultMap);
    }

    @RequestMapping(value = "/ordersRank", method = {RequestMethod.GET})
    public RestResponse<PageInfo<Map<String, Object>>> ordersRank(@RequestParam(defaultValue = "1") int pageNum,
                                                                  @RequestParam(defaultValue = "4") int pageSize,
                                                                  @RequestParam(value = "gmtStart") String gmtStart,
                                                                  @RequestParam(value = "gmtEnd") String gmtEnd,
                                                                  @RequestParam(value = "sortKey",defaultValue = "id") String sortKey,
                                                                  @RequestParam(value = "sortOrder",defaultValue = "desc") String sortOrder) {
        PageInfo<Map<String, Object>> listPageInfo = new PageInfo<>();

        listPageInfo.setTotal(statMapper.countOrdersRank(gmtStart, gmtEnd));
        listPageInfo.setList(statMapper.ordersRank((pageNum - 1) * pageSize, pageSize, gmtStart, gmtEnd, sortKey, sortOrder));

        return RestResponse.getSuccesseResponse(listPageInfo);
    }

    @RequestMapping(value = "/getSalesMoneyStat", method = {RequestMethod.GET})
    public RestResponse<Map<String, Object>> getSalesMoneyStat() {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("totalSalesMoney", statMapper.getTotalSaleMoney(new Date(0L), new Date()));
        resultMap.put("lastMonthSalesMoney", statMapper.getTotalSaleMoney(getLastMonthBegin(), getLastMonthEnd()));
        resultMap.put("lastlastMonthSalesMoney", statMapper.getTotalSaleMoney(getLastLastMonthBegin(), getLastLastMonthEnd()));
        resultMap.put("yesterdaySalesMoney", statMapper.getTotalSaleMoney(getLastDayBegin(), getLastDayEnd()));
        resultMap.put("totalOrdersCount", statMapper.getTotalOrders());

        return RestResponse.getSuccesseResponse(resultMap);
    }
    @RequestMapping(value = "/getSalesMoneyTrend", method = {RequestMethod.GET})
    public RestResponse<List<Map<String, BigDecimal>>> getSalesMoneyTrend(
            @RequestParam(value = "gmtStart") String gmtStart,
            @RequestParam(value = "gmtEnd") String gmtEnd
    ) {
        return RestResponse.getSuccesseResponse(statMapper.getSalesMoneyTrend(gmtStart, gmtEnd));
    }

    @RequestMapping(value = "/getYearStat", method = {RequestMethod.GET})
    public RestResponse<List<Map<String, Object>>> getYearStat(
            @RequestParam(value = "year") Integer year
    ) {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (int i=1; i<13; i++) {
            Map<String, Object> monthResult = new TreeMap<>();
            Date start = getMonthBegin(year, i);
            Date end = getMonthEnd(year, i);
            monthResult.put("id", i);
            monthResult.put("month", String.format("%d-%02d", year, i));
            monthResult.put("totalSoldMoney", statMapper.getTotalSaleMoney(start, end));
            monthResult.put("alipayTotalMoney", statMapper.getTotalSaleMoneyByPayMode(start, end, "alipay"));
            monthResult.put("alipayTotalCount", statMapper.getOrdersCountByTimeByPayMode(start, end, "alipay"));
            monthResult.put("wechatTotalMoney", statMapper.getTotalSaleMoneyByPayMode(start, end, "wechat"));
            monthResult.put("wechatTotalCount", statMapper.getOrdersCountByTimeByPayMode(start, end, "wechat"));
            monthResult.put("orderCount", statMapper.getOrdersCountByTime(start, end));
            monthResult.put("totalRebateMoney", statMapper.getTotalRebateMoney(start, end));
            resultMap.add(monthResult);
        }

        Map<String, Object> sumResult = new TreeMap<>();
        sumResult.put("id", 13);
        sumResult.put("month", "合计");
        sumResult.put("totalSoldMoney", resultMap.stream().map(x -> (BigDecimal)x.get("totalSoldMoney")).reduce(BigDecimal.ZERO, BigDecimal::add));
        sumResult.put("alipayTotalMoney", resultMap.stream().map(x -> (BigDecimal)x.get("alipayTotalMoney")).reduce(BigDecimal.ZERO, BigDecimal::add));
        sumResult.put("alipayTotalCount", resultMap.stream().mapToLong(x -> (Long)x.get("alipayTotalCount")).sum());
        sumResult.put("wechatTotalMoney", resultMap.stream().map(x -> (BigDecimal)x.get("wechatTotalMoney")).reduce(BigDecimal.ZERO, BigDecimal::add));
        sumResult.put("wechatTotalCount", resultMap.stream().mapToLong(x -> (Long)x.get("wechatTotalCount")).sum());
        sumResult.put("orderCount", resultMap.stream().mapToLong(x -> (Long)x.get("orderCount")).sum());
        sumResult.put("totalRebateMoney", resultMap.stream().map(x -> (BigDecimal)x.get("totalRebateMoney")).reduce(BigDecimal.ZERO, BigDecimal::add));
        resultMap.add(sumResult);

        return RestResponse.getSuccesseResponse(resultMap);
    }

    public Date getLastDayBegin() {
        //昨天零点
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN).atZone(zone).toInstant());
    }

    public Date getLastDayEnd() {
        //昨天结束
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX).atZone(zone).toInstant());
    }

    public Date getLastMonthBegin() {
        LocalDate today = LocalDate.now();
        LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth().minus(1), 1);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(firstday.atStartOfDay(zone).toInstant());
    }

    public Date getLastMonthEnd() {
        LocalDate today = LocalDate.now();
        LocalDate lastday = today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(LocalDateTime.of(lastday, LocalTime.MAX).atZone(zone).toInstant());
    }

    public Date getLastLastMonthBegin() {
        LocalDate today = LocalDate.now();
        LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth().minus(2), 1);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(firstday.atStartOfDay(zone).toInstant());
    }

    public Date getLastLastMonthEnd() {
        LocalDate today = LocalDate.now();
        LocalDate lastday = today.minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(LocalDateTime.of(lastday, LocalTime.MAX).atZone(zone).toInstant());
    }

    public Date getMonthBegin(int year, int month) {
        LocalDate firstday = LocalDate.of(year, month, 1);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(firstday.atStartOfDay(zone).toInstant());
    }

    public Date getMonthEnd(int year, int month) {
        LocalDate lastday = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(LocalDateTime.of(lastday, LocalTime.MAX).atZone(zone).toInstant());
    }

    public ShipInOut fromRowToShipInOut(Row row) {
        ShipInOut shipInOut = new ShipInOut();
//        shipInOut.setId(row.getCell(0).getStringCellValue());
        shipInOut.setShipNameCn(row.getCell(0).getStringCellValue());
//        shipInOut.setShipIdentity(row.getCell(1).getStringCellValue());
//        shipInOut.setTotalTon(row.getCell(3).getNumericCellValue());
//        shipInOut.setWeightTon(row.getCell(4).getNumericCellValue());
//        shipInOut.setPower(row.getCell(5).getNumericCellValue());
        shipInOut.setShipType(row.getCell(1).getStringCellValue());
        shipInOut.setShipBorn(row.getCell(2).getStringCellValue());
//        shipInOut.setShipLength(row.getCell(8).getNumericCellValue());
//        shipInOut.setShipWidth(row.getCell(9).getNumericCellValue());
        shipInOut.setInOutType(row.getCell(3).getStringCellValue());
        shipInOut.setShipOrg(row.getCell(4).getStringCellValue());
        shipInOut.setApplyDateTime(row.getCell(5).getDateCellValue());
//        shipInOut.setPreInOutDateTime(row.getCell(13).getDateCellValue());
        shipInOut.setPortName(row.getCell(6).getStringCellValue());
        shipInOut.setParkName(row.getCell(7).getStringCellValue());
        shipInOut.setUpDownPort(row.getCell(8) == null ? "" : row.getCell(8).getStringCellValue());
//        shipInOut.setRegularCert(row.getCell(17).getStringCellValue());
//        shipInOut.setShipCount(row.getCell(18).getNumericCellValue());
        shipInOut.setRealWeightTon(row.getCell(9).getNumericCellValue());
        shipInOut.setLoadingTon(row.getCell(10).getNumericCellValue());
        shipInOut.setRealPassengerCount(row.getCell(11).getNumericCellValue());
        shipInOut.setUpDownPassengerCount(row.getCell(12).getNumericCellValue());
        shipInOut.setGoodsType(row.getCell(13) == null ? "" : row.getCell(13).getStringCellValue());
        return shipInOut;
    }

    public void readExcel(List<ShipInOut> shipInOutList, String fileName) throws IOException, InvalidFormatException {
        InputStream is = new ClassPathResource(fileName).getInputStream();
        Workbook wb = null;
        int i = 0;
        try {
            wb = WorkbookFactory.create(is);

            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                if (i == 0) {
                    i++;
                    continue;
                }
                System.out.println("read line " + i);
                i++;
                ShipInOut shipInOut = fromRowToShipInOut(row);
                shipInOutList.add(shipInOut);
            }
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        StatController statController = new StatController();
        List<ShipInOut> shipInOutList = new ArrayList<>(1500000);
        List<String> fileList = Arrays.asList(
                "20180817 下一港浙江省地方海事局2017年7月至12月 进出港报告数据.xlsx",
                "20180817 下一港浙江省地方海事局2018年1月至8月16日进出港报告数据.xlsx",
                "2017.7.xlsx",
                "2017.8.xlsx",
                "2017.9.xlsx",
                "2017.10.xlsx",
                "2017.11.xlsx",
                "2017.12.xlsx",
                "2018.1.xlsx",
                "2018.2.xlsx",
                "2018.3.xlsx",
                "2018.4.xlsx",
                "2018.5.xlsx",
                "2018.6.xlsx",
                "2018.7.xlsx"
        );
        for (String s : fileList) {
            System.out.println("add file " + s);
            statController.readExcel(shipInOutList, s);
        }

        Map<String, Map<Double, Map<String, List<ShipInOut>>>> map =
                shipInOutList.stream().collect(Collectors.groupingBy(
                        ShipInOut::getShipNameCn,
                        Collectors.groupingBy(
                                ShipInOut::getLoadingTon,
                                Collectors.groupingBy(
                                        ShipInOut::getGoodsType
                                )
                        )
                ));

        List<String> writerList = new ArrayList<>(1500000);
        map.entrySet().forEach(
                shipNameCN -> {
                    shipNameCN.getValue().entrySet().forEach(
                            loadingTon -> {
                                loadingTon.getValue().entrySet().forEach(
                                        goodsType -> {
                                            List<ShipInOut> shipInOuts = goodsType.getValue().stream().sorted(Comparator.comparing(ShipInOut::getApplyDateTime)).collect(Collectors.toList());

                                            int i = 0;
                                            while (i < shipInOuts.size()) {
                                                ShipInOut thisTime = shipInOuts.get(i);
                                                if ("进港".equals(thisTime.getInOutType())) {
                                                    writerList.add(thisTime.toString());
                                                    i++;
                                                } else if ("出港".equals(thisTime.getInOutType())) {
                                                    if (i + 1 >= shipInOuts.size()) {
                                                        // 已经是最后一个了
                                                        writerList.add(thisTime.toString());
                                                        i++;
                                                    } else {
                                                        // 判断下一个是否是进港,并且关联
                                                        ShipInOut next = shipInOuts.get(i + 1);
                                                        if ("进港".equals(next.getInOutType())) {
                                                            if (thisTime.getUpDownPort().equals(next.getShipOrg())) {
                                                                // 只留出港数据
                                                                writerList.add(thisTime.toString());
                                                                i += 2;
//                                                                System.out.println(
//                                                                        String.format(
//                                                                                "drop %s for same record %s",
//                                                                                next.getId(), thisTime.getId()
//                                                                        )
//                                                                );
                                                                System.out.println(
                                                                    String.format(
                                                                            "drop %s for same record %s",
                                                                            next.getShipNameCn(), thisTime.getShipNameCn()
                                                                    )
                                                                );
                                                            } else {
                                                                writerList.add(shipInOuts.get(0).toString());
                                                                i++;
                                                            }
                                                        } else {
                                                            // 下一个是出港,跟本次不关联
                                                            writerList.add(shipInOuts.get(0).toString());
                                                            i++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                );
                            }
                    );
                }
        );

        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream("result.csv"),"gbk");
            for (String s : writerList) {
                out.write(s + "\n");
            }

            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
