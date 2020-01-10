package com.example.xbd_pay.uitls;

import java.util.Date;
import java.util.Random;

/**
 * 流水号生成
 */
public class SingleSerialFactoryUtil {
    private static SingleSerialFactoryUtil factory = new SingleSerialFactoryUtil();
    // 随机串范围
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
            .toCharArray();

    private SingleSerialFactoryUtil() {
    }
    /**
     * 获取工厂实例
     *
     * @return
     */
    public static SingleSerialFactoryUtil getInstance() {
        return factory;
    }

    /**
     * 生成流水号prefix + yyyyMMddHHmmssSSS + 4个随机数
     * @return
     */
    private static String createStreamNumber() {
        int orderLength = 25;
        StringBuffer orderNo = new StringBuffer();
        orderNo.append("XBDPAY" + DateUtils.toString(new Date(), DateUtils.PATTERN4));
        orderNo.append(randomString(orderLength - orderNo.length()));
        return orderNo.toString();
    }

    // 产生随机的数字+字母串
    public static String randomString(int length) {
        if (length < 1) {
            return "";
        }
        Random strGen = new Random();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[strGen
                    .nextInt(numbersAndLetters.length - 1)];
        }
        return new String(randBuffer);
    }
}
