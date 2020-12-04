package com.ms.icbc.security.utils;

/**
 * 
* @author Dabing
* @E-mail chenbinging@outlook.com
* @date 2018-12-2涓婂崍10:56:50
* 鍗佸叚杩涘埗杞崲
 */
public class HexStringUtil {

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static String byte2HexNew(byte[] bArray) {
		if(null == bArray) {
			throw new IllegalArgumentException("param is null");
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp);
		}
		return sb.toString();
	}

	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	
	/*
	 * 将字符串转换为字母,10以上转换，10-a,11-b
	 */
	public static String numToAlpha(int num) {
		String result = "";
		if (num < 10) {
			result = num + "";
		} else {
			switch (num) {
			case 10:
				result = "a";
				break;
			case 11:
				result = "b";
				break;
			case 12:
				result = "c";
				break;
			case 13:
				result = "d";
				break;
			case 14:
				result = "e";
				break;
			case 15:
				result = "f";
				break;
			case 16:
				result = "g";
				break;

			case 17:
				result = "h";
				break;

			case 18:
				result = "i";
				break;

			case 19:
				result = "j";
				break;

			}
		}
		return result;

	}
}
