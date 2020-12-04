package com.ms.icbc.security;


import java.io.UnsupportedEncodingException;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ms.icbc.security.bean.EnvInfo;
import com.ms.icbc.security.bean.FingerField;
import com.ms.icbc.security.utils.EncodeUtils;
import com.ms.icbc.security.utils.HexStringUtil;
import com.ms.icbc.security.utils.StringUtils;
import com.ms.icbc.security.utils.SysLog;







public class CommonUtils {
	public static final String PHANTOMJS = "PhantomJS";
	public static int score = 100;

	/**
	 * 根据传递进来的http请求头来判断
	 * 
	 * @param requestHeader
	 *            根据请求头判断客户端是否使用PhantomJS在操作
	 * @return
	 */
	public static boolean isContainPhantomJS(String requestHeader) {
		if(null == requestHeader) {
			throw new IllegalArgumentException("requesterHeader is null");
		}
		if (requestHeader.contains(PHANTOMJS)) {
			return true;
		} else {
			return false;
		}
	}

	public static void parseClientFinger(FingerField field) {
		if(null == field) {
			throw new IllegalArgumentException("field is null");
		}
		if (true) {
			SysLog.println("st = " + field.st);
			SysLog.println("ua = " + field.ua);
			SysLog.println("resolution = " + field.resolution);
			SysLog.println("can = " + field.can);
			SysLog.println("gi = " + field.gi);
			SysLog.println("hlb = " + field.hlb);
			SysLog.println("hlr = " + field.hlr);
			SysLog.println("np = " + field.np);
			SysLog.println("timeZone = " + field.timeZone);
			SysLog.println("language = " + field.language);
			SysLog.println("rp = " + field.rp);
			SysLog.println("hc = " + field.hc);
			SysLog.println("pr = " + field.pr);
			SysLog.println("cpt = " + field.cpt);
			SysLog.println("ls = " + field.ls);
			SysLog.println("ss = " + field.ss);
			SysLog.println("ind = " + field.ind);
			SysLog.println("web = " + field.web);
		}
		handleField(field);
	}
	
	public static void parseClientFingerWithEn(String  content) {
		if(null == content) {
			throw new IllegalArgumentException("content is null");
		}
		String fieldString = dtStr("123456", content);
		
		SysLog.println("fieldString = " + fieldString);
		FingerField field = JSON.parseObject(fieldString,FingerField.class);
		if (true) {
			SysLog.println("st = " + field.st);
			SysLog.println("ua = " + field.ua);
			SysLog.println("resolution = " + field.resolution);
			SysLog.println("can = " + field.can);
			SysLog.println("gi = " + field.gi);
			SysLog.println("hlb = " + field.hlb);
			SysLog.println("hlr = " + field.hlr);
			SysLog.println("np = " + field.np);
			SysLog.println("timeZone = " + field.timeZone);
			SysLog.println("language = " + field.language);
			SysLog.println("rp = " + field.rp);
			SysLog.println("hc = " + field.hc);
			SysLog.println("pr = " + field.pr);
			SysLog.println("cpt = " + field.cpt);
			SysLog.println("ls = " + field.ls);
			SysLog.println("ss = " + field.ss);
			SysLog.println("ind = " + field.ind);
			SysLog.println("web = " + field.web);
		}
		handleField(field);
	}
	
	

	private static void handleField(FingerField field) {
		if(null == field) {
			throw new IllegalArgumentException("field is null");
		}
		String resolution = field.getResolution();
		String[] screentInfo = resolution.split(":");
		SysLog.println("width = " + screentInfo[0]);
		SysLog.println("height = " + screentInfo[1]);
		// 分辨率判断
		if (Integer.parseInt(screentInfo[0]) < 1024
				|| Integer.parseInt(screentInfo[1]) < 768) {
			score -= 10;
		}
		
		if(field.pr > 15){
			score -= 10;
		}
		// 判断CPU核心数
		if (field.hc < 1 || field.hc > 32) {
			score -= 10;
		}
		// 浏览器色深
		if (field.cd > 48) {
			score -= 10;
		}
		if(field.ss == true){
			score -= 10;
		}

		if(field.st==""){
			score -= 10;
		}
		// 检测webdriver
		if (field.ua.contains("phantomjs")) {
			score -= 10;
		}
		// 指纹检测
		if (field.can.equals("") || field.web.equals("")) {
			score -= 10;
		}

		// 浏览器
		if (field.hlb) {
			score -= 10;
		}

		// 伪造分辨率
		if (field.hlr) {
			score -= 10;
		}
		SysLog.println("score = " + score);
	}

	private static byte[] init3DesKey(String str) {
		if(null == str) {
			throw new IllegalArgumentException("key is null");
		}
		byte[] key = null;
		if (StringUtils.isEmpty(str)) {
			return key;
		}
		byte[] iKey = EncodeUtils.encryptBySha(str);
		if (iKey == null) {
			return null;
		}
		String hexStr = HexStringUtil.byte2HexNew(iKey);
		String keyStr = "";
		if (hexStr.length() > 28) {
			keyStr = hexStr.substring(4, 28);
		} else {
			for (int i = 0; i < 24; i++) {
				if (hexStr.length() >= (i + 4)) {
					char c = hexStr.charAt(i + 4);
					keyStr.concat(String.valueOf(c));
				} else {
					keyStr.concat("0");
				}
			}
		}
		/*
		 * for (int i = 0; i < key.length; i++) { key[i] = iKey[i + 4]; }
		 */
		try {
			key = keyStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return key;
	}
	
	public static String dtStr(String key, String str) {
		if(null == key||null == str) {
			throw new IllegalArgumentException("param is null");
		}
		String s = "";
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(key)) {
			return s;
		}
		byte[] base64 = EncodeUtils.decode2Base64(str);

		
		SysLog.println("dtStr1:" + key);
		
		byte[] keys = init3DesKey(key);

		if (keys == null || base64 == null) {

			return s;
		}
		byte[] dtbyte = EncodeUtils.decryptBy3DesCBC(keys, base64);
		if (dtbyte == null) {

			return s;
		}
		try {
			s = new String(dtbyte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		base64 = null;
		key = null;
		return s;
	}
	/**
	 * 添加优化算法
	 * @param envInfo
	 */
	public static void parseClientDriver(EnvInfo envInfo) {
		if(null == envInfo) {
			throw new IllegalArgumentException("envInfo is null");
		}

		SysLog.println("webdriver = " + envInfo.webdriver);
		SysLog.println("phantomJS = " + envInfo.phantomJS);
		
		handleClientDriover(envInfo);
	}
	
	public static void parseClientDriverEn(String content) {
		if(null == content) {
			throw new IllegalArgumentException("cintent is null");
		}

		String envInfoString = dtStr("123456", content);
		
		SysLog.println("envInfoString = " + envInfoString);
		EnvInfo envInfo = JSON.parseObject(envInfoString,EnvInfo.class);
		
		
		SysLog.println("webdriver = " + envInfo.webdriver);
		SysLog.println("phantomJS = " + envInfo.phantomJS);
		
		handleClientDriover(envInfo);
	}

	private static void handleClientDriover(EnvInfo envInfo) {
		if(null == envInfo) {
			throw new IllegalArgumentException("envInfo is null");
		}

		// TODO Auto-generated method stub
		if(envInfo.phantomJS == true){
			score -= 10;
		}
		
		if(envInfo.webdriver == true){
			score -= 10;
		}
		
	}
	
	
	public static int getScore(){
		return score;
	}

}
