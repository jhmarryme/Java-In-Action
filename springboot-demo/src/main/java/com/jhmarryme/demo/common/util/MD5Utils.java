package com.jhmarryme.demo.common.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class MD5Utils {

	/**
     * 对字符串进行md5加密
     *
     * @param strValue 需要加密的字符串
     * @return java.lang.String
     * @throws Exception e
     */
	public static String getMd5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
	}

}
