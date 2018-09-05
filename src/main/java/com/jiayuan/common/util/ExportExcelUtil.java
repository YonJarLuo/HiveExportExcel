package com.jiayuan.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by YonJar on 2018/8/23.
 * 导出Excel表工具类
 */
public class ExportExcelUtil {

    HSSFWorkbook wb = new HSSFWorkbook();   //工作薄

    public void export(List<Map<String,String>> list,String descPath) throws Exception {
        if (descPath.endsWith(".xls")){
            export03(list,descPath);
        }else{
            export07(list,descPath);
        }

    }

    //导出03版本的Excel
    public void export03(List<Map<String,String>> list,String descPath) throws Exception {

        HSSFSheet sheet = wb.createSheet("table");//创建表单
        HSSFRow row;    //行
        HSSFCell cell;  //列

        //判断list不为空，且长度大于1  过滤只有表而表中无数据的表
        if (!list.isEmpty() && list.size() > 1){
            for (int i = 0; i < list.size() + 1; i++) {     //小于list.size()+1 ,因为excel表格第一行填充的是字段的备注名 占用了1行
                row = sheet.createRow(i);
                for (int j = 0; j < list.get(0).size(); j++) {
                    cell = row.createCell(j);

                    Set<String> keySet = list.get(0).keySet();     //因为传的是一个list，而list里面装了一个map。list里面.get(0)获取的是字段的所有备注
                    Object[] array = keySet.toArray();

                    if (i == 0){    //第一行，直接设置字段的备注名
                        cell.setCellValue(array[j].toString());
                    }else {
                        //第二行开始 根据key备注名，获取value
                        String value = list.get(i-1).get(array[j].toString());      //因为第一层for循环+1，此处-1，不然会下标越界异常
                        //加一层判断，预防字段值长度超过Excel表格的单元格的最大值
                        if (value != null && value.length() > 32767){
                            //因为有些字段是存储图数据的，导出Excel表并展示无意义
                           continue;
                        }
                        cell.setCellValue(value);
                    }
                }
            }
            //循环之后进行写操作
            wb.write(new FileOutputStream(descPath));
        }else{
            System.out.println("list 为空");
        }
    }

    //导出07版本
    public void export07(List<Map<String,String>> list,String descPath) throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook();
        XSSFSheet xsheet = xwb.createSheet("table");
        Row xrow;
        Cell xcell;

        if (!list.isEmpty() && list.size() > 1){
            for (int i = 0; i < list.size() + 1; i++) {
                xrow = xsheet.createRow(i);
                for (int j = 0; j < list.get(0).size(); j++) {
                    xcell = xrow.createCell(j);

                    Set<String> keySet = list.get(0).keySet();
                    Object[] array = keySet.toArray();

                    if (i == 0){    //第一行，直接设置字段的备注名
                        xcell.setCellValue(array[j].toString());
                    }else {
                        //第二行开始 根据key备注名，获取value
                        String value = list.get(i-1).get(array[j].toString());
                        if (value != null && value.length() > 32767){
                            continue;
                        }
                        xcell.setCellValue(value);
                    }
                }
            }
            wb.write(new FileOutputStream(descPath));
        }else{
            System.out.println("list 为空");
        }

    }
}
