package com.sherlockkk.snail.tools;

/**
 * @author SongJian
 * @created 2016/3/12.
 * @e-mail 1129574214@qq.com
 */
public class ToolString {
    /*去除字符串中的所有空格*/
    public static String removeAllSpace(String str) {
        String tmpstr = str.replace(" ", "");
        return tmpstr;
    }
}
