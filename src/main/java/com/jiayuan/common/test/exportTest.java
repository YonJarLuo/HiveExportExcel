package com.jiayuan.common.test;

import com.jiayuan.common.util.ExportExcelUtil;
import com.jiayuan.common.util.HiveConnectUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by YonJar on 2018/8/23.
 */
public class exportTest {

    //只是打印有备注字段的表
    @Test
    public void test01(){
        String tablename = "mylike";
        String sql = "select * from "+tablename+" limit 10";
        String descPath = "/root/myfile.xls";
        HiveConnectUtil connectUtil = new HiveConnectUtil();

        ArrayList<Map<String,String>> dataList = new ArrayList<Map<String, String>>();
        LinkedHashMap<String,String> dataMap = null;

        try {
            Connection connect = connectUtil.getConnect();
            Map<String, String> tableColumn = connectUtil.getTableColumn(connect, tablename);

            //使用ResultSetMetaData ,因为 DatabaseMetaData会查询出隐藏的字段
            ResultSet resultSet = connectUtil.queryTable(connect, sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int length = metaData.getColumnCount();     //实际可见的字段列数

            while (resultSet.next()){
                dataMap = new LinkedHashMap<String, String>();
                for (int i = 1;i < length+1;i++){   //字段名和值关联
                    String columnName = metaData.getColumnName(i);
                    int index = columnName.indexOf(".");
                    String substring = columnName.substring(index + 1);
                    System.out.println(tableColumn.get(substring)+": "+resultSet.getString(i));
                    dataMap.put(tableColumn.get(substring),resultSet.getString(i));
                }
                dataList.add(dataMap);
            }
            connectUtil.closeConnect();

            //export
            ExportExcelUtil excelUtil = new ExportExcelUtil();
            excelUtil.export(dataList,descPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
