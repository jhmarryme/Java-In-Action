package com.jhmarryme.demo.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 来自 天天吃货的工具类
 * @author Jiahao Wang
 * @date 2021/4/1 10:20
 */
public class MobileEmailUtils {

    public static boolean checkMobileIsOk(String mobile) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean checkEmailIsOk(String email) {
        boolean isMatch = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            isMatch = false;
        }
        return isMatch;
    }
}

