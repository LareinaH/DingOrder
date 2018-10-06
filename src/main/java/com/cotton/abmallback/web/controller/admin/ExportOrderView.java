package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.enumeration.OrderReturnStatusEnum;
import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.model.OrderGoods;
import com.cotton.abmallback.model.Orders;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportOrderView extends ExcelView {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void setRow(Sheet sheet, Map<String, Object> map) {
        Row header = sheet.createRow(0);
        int cellIndex = 0;
        header.createCell(cellIndex).setCellValue("订单号");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("下单时间");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("订单来源");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("收件人姓名");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("收件人手机号");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("收件人联系方式");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("商品详情");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("实付金额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("总返利金额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("订单状态");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("快递单号");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("补货状态");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("订单变更时间");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("订单会员手机号");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        List<Orders> ordersList = (List<Orders>)map.get("detail");
        Map<Long, OrderGoods> orderGoodsMap = (Map<Long, OrderGoods>)map.get("orderGoods");
        AtomicInteger rowIndex = new AtomicInteger(1);
        ordersList.forEach(x -> {
            int ci = 0;
            Row row = sheet.createRow(rowIndex.getAndIncrement());
            row.createCell(ci++).setCellValue(x.getOrderNo());
            row.createCell(ci++).setCellValue(sdf.format(x.getGmtCreate()));
            row.createCell(ci++).setCellValue(x.getOrderSource());
            row.createCell(ci++).setCellValue(x.getReceiverName());
            row.createCell(ci++).setCellValue(x.getReceiverPhone());
            row.createCell(ci++).setCellValue(
                    String.format(
                            "%s省%s市%s区%s",
                            x.getReceiverProvinceName(),
                            x.getReceiverCityName(),
                            x.getReceiverCountyName(),
                            x.getReceiverAddress()
                    )
            );
            if (orderGoodsMap.containsKey(x.getId())) {
                OrderGoods og = orderGoodsMap.get(x.getId());
                row.createCell(ci++).setCellValue(
                        String.format(
                                "%s,%s,%s * %d",
                                og.getGoodsSpecificationNo(),
                                og.getGoodName(),
                                og.getGoodsSpecificationName(),
                                og.getGoodNum()
                        )
                );
            } else {
                row.createCell(ci++).setCellValue("未查询到商品详情");
            }
            row.createCell(ci++).setCellValue(x.getTotalMoney().toString());
            row.createCell(ci++).setCellValue(x.getRebateMoney().toString());
            row.createCell(ci++).setCellValue(OrderStatusEnum.valueOf(x.getOrderStatus()).getDisplayName());
            row.createCell(ci++).setCellValue(x.getLogisticCode());
            row.createCell(ci++).setCellValue(OrderReturnStatusEnum.valueOf(x.getReturnStatus()).getDisplayName());
            row.createCell(ci++).setCellValue(sdf.format(x.getGmtModify()));
            row.createCell(ci++).setCellValue(x.getMemberPhone());
        });
    }

    @Override
    protected void setStyle(Workbook workbook) {
        super.cellStyle = new DefaultCellStyleImpl().setCellStyle(workbook);
    }
}
