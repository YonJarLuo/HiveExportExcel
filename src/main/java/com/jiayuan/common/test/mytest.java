package com.jiayuan.common.test;

import org.junit.Test;

import java.util.Scanner;

/**
 * Created by YonJar on 2018/9/4.
 */
public class mytest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文字：");
        String str = scanner.nextLine();
        System.out.println("输入的数据是："+str+"  输入的类型是："+str.getClass().getName());

        System.out.println("请输入数字：");
        Integer i = scanner.nextInt();
        System.out.println("输入的数据是："+i+"  输入的类型是："+i.getClass().getName());

    }


}
