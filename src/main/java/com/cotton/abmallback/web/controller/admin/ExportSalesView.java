package com.cotton.abmallback.web.controller.admin;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportSalesView extends ExcelView {
    @Override
    protected void setRow(Sheet sheet, Map<String, Object> map) {
        Row header = sheet.createRow(0);
        int cellIndex = 0;
        header.createCell(cellIndex).setCellValue("月份");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("销售额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("支付宝金额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("支付宝单数");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("微信金额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("微信单数");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("订单量");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("返利额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);

        List<Map<String, Object>> salesList = (List<Map<String, Object>>)map.get("detail");
        AtomicInteger rowIndex = new AtomicInteger(1);
        salesList.forEach(x -> {
            int ci = 0;
            Row row = sheet.createRow(rowIndex.getAndIncrement());
            row.createCell(ci++).setCellValue((String)x.get("month"));
            row.createCell(ci++).setCellValue(((BigDecimal)x.get("totalSoldMoney")).toPlainString());
            row.createCell(ci++).setCellValue(((BigDecimal)x.get("alipayTotalMoney")).toPlainString());
            row.createCell(ci++).setCellValue(String.valueOf(x.get("alipayTotalCount")));
            row.createCell(ci++).setCellValue(((BigDecimal)x.get("wechatTotalMoney")).toPlainString());
            row.createCell(ci++).setCellValue(String.valueOf(x.get("wechatTotalCount")));
            row.createCell(ci++).setCellValue(String.valueOf(x.get("orderCount")));
            row.createCell(ci++).setCellValue(((BigDecimal)x.get("totalRebateMoney")).toPlainString());
        });
    }

    @Override
    protected void setStyle(Workbook workbook) {
        super.cellStyle = new DefaultCellStyleImpl().setCellStyle(workbook);
    }
}
