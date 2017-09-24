package edu.ccnt.mymall.util;

import java.math.BigDecimal;

/**
 * Created by LXY on 2017/9/24.
 */
public class BigDecimalUtil {

    private BigDecimalUtil(){

    }

    public static BigDecimal add(double num1,double num2){     //相加
        BigDecimal a = new BigDecimal(Double.toString(num1));
        BigDecimal b = new BigDecimal(Double.toString(num2));
        return a.add(b);
    }

    public static BigDecimal sub(double num1,double num2){     //相减
        BigDecimal a = new BigDecimal(Double.toString(num1));
        BigDecimal b = new BigDecimal(Double.toString(num2));
        return a.subtract(b);
    }

    public static BigDecimal mul(double num1,double num2){     //相乘
        BigDecimal a = new BigDecimal(Double.toString(num1));
        BigDecimal b = new BigDecimal(Double.toString(num2));
        return a.multiply(b);
    }

    public static BigDecimal div(double num1,double num2){     //相除
        BigDecimal a = new BigDecimal(Double.toString(num1));
        BigDecimal b = new BigDecimal(Double.toString(num2));
        return a.divide(b,2,BigDecimal.ROUND_HALF_UP);
    }
}
