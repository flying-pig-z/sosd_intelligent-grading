package com.flyingpig.util;

import java.text.NumberFormat;

public class DataAnalysisUtil {
    public static String parseToPercentage(double part,double total){
        double percentage = part / total * 100;

        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2); // 设置小数点后保留两位

        return percentFormat.format(percentage / 100); // 注意：format方法接收的是一个0-1之间的数
    }


    public static void main(String[] args) {
        System.out.println(parseToPercentage(50,100));
    }
}
