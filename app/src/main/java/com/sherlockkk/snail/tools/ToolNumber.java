package com.sherlockkk.snail.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SongJian
 * @created 2016/3/12.
 * @e-mail 1129574214@qq.com
 */
public class ToolNumber {
    /**
     * 判断是否为纯数字
     * @param number
     * @return
     */
    public static boolean isPureDigit(String number){
        Pattern pattern  = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(number);
        boolean result = matcher.matches();
        if (result == true){
            return true;
        }else {
            return false;
        }
    }
}
