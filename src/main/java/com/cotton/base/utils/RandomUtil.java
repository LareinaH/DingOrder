package com.cotton.base.utils;

import java.util.Random;

/**
 * RandomUtil
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/21
 */
public class RandomUtil {

    public static String getRandomNumCode(int count){

        StringBuilder codeNum = new StringBuilder();

        int [] numbers = {0,1,2,3,4,5,6,7,8,9};

        Random random = new Random();

        for (int i = 0; i < count; i++) {

            int next = random.nextInt(10000);

            codeNum.append(numbers[next % 10]);
        }

        return codeNum.toString();
    }
}
