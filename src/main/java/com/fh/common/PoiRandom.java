package com.fh.common;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class PoiRandom {
    public static void main(String[] args) throws Exception {
        //获取流
        InputStream inputStream = new FileInputStream("F:\\1908A班学生信息.xlsx");
        // 2、讲流交给workbook解析
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        // 3、得到Excel工作表对象
        XSSFSheet sheetAt = workbook.getSheetAt(1);
        List list = new ArrayList();
        // 4、循环读取表格数据
        for (Row row : sheetAt) {
            // 首行不读取
            if (row.getRowNum() == 0 || row.getRowNum() == 1) {
                continue;
            }
            // 读取当前行中单元格数据，索引从0开始
            String userName = row.getCell(0).getStringCellValue();
            list.add(userName);
        }
        Random rand = new Random();
        int i = rand.nextInt(list.size());
        Object o = list.get(i);
        System.out.println(o.toString());
        // 5、关闭流
        workbook.close();
    }
}
