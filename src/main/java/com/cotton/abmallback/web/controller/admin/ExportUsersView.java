package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.enumeration.MemberLevelEnum;
import com.cotton.abmallback.model.vo.admin.MemberVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportUsersView extends ExcelView {
    @Override
    protected void setRow(Sheet sheet, Map<String, Object> map) {
        Row header = sheet.createRow(0);
        int cellIndex = 0;
        header.createCell(cellIndex).setCellValue("会员昵称");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("注册时间");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("引荐人昵称");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("会员级别");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("手机号");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("团队中V3数目");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("团队中V2数目");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("团队中V1数目");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("团队中代言人数目");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("团队中小白数目");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("直推人数");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("名下团队人数");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("总购物金额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("有效订单数目");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);
        header.createCell(cellIndex).setCellValue("返利总金额");
        header.getCell(cellIndex++).setCellStyle(super.cellStyle);

        List<MemberVO> salesList = (List<MemberVO>)map.get("detail");
        AtomicInteger rowIndex = new AtomicInteger(1);
        salesList.forEach(x -> {
            int ci = 0;
            Row row = sheet.createRow(rowIndex.getAndIncrement());
            row.createCell(ci++).setCellValue(x.getName());
            row.createCell(ci++).setCellValue(DateFormatUtils.format(x.getGmtCreate(), "yyyy-MM-dd hh:mm:ss"));
            row.createCell(ci++).setCellValue(x.getReferrerName());
            row.createCell(ci++).setCellValue(MemberLevelEnum.valueOf(x.getLevel()).getDisplayName());
            row.createCell(ci++).setCellValue(x.getPhoneNum());
            row.createCell(ci++).setCellValue(x.getTeamV3Count());
            row.createCell(ci++).setCellValue(x.getTeamV2Count());
            row.createCell(ci++).setCellValue(x.getTeamV1Count());
            row.createCell(ci++).setCellValue(x.getTeamAgentCount());
            row.createCell(ci++).setCellValue(x.getTeamWhiteCount());
            row.createCell(ci++).setCellValue(x.getReferTotalAgentCount());
            row.createCell(ci++).setCellValue(x.getReferTotalCount());
            row.createCell(ci++).setCellValue(x.getMoneyTotalSpend().toPlainString());
            row.createCell(ci++).setCellValue(x.getOrdersCount());
            row.createCell(ci++).setCellValue(x.getMoneyTotalEarn().toPlainString());
        });
    }

    @Override
    protected void setStyle(Workbook workbook) {
        super.cellStyle = new DefaultCellStyleImpl().setCellStyle(workbook);
    }
}
