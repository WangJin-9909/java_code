package utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str ="一二三四五abced";
		// 测试base64编码
		String str_base = encodeBase64(str);
		System.out.println("编码后长度 ： " + str_base.length());
		System.out.println(str_base);
		System.out.println(decodeBase64("5LiA5LqM5LiJ5Zub5LqUYWJjZWQ="));
		
		
		// 测试base64解码
		//System.out.println(decodeBase64("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"));
	}

	public static String encodeBase64(String str) {
		return new String(Base64.getEncoder().encode(str.getBytes()));
	}

	public static String decodeBase64(String str) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(str.getBytes()), "UTF-8");
	}
}
