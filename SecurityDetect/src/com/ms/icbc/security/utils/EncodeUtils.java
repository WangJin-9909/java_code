/**
* @author Dabing
* @E-mail chenbinging@outlook.com
* @date 2018-12-7娑撳宕�5:49:36
* 閸旂姴鐦戝銉ュ徔缁拷
*/
package com.ms.icbc.security.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.com.icbc.ms.token.utils.Base64;
import cn.com.icbc.ms.token.utils.SysLog;



public class EncodeUtils {
	
	public static byte[] encryptBySha(String str) {
		if(null == str){
			return null;
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bts = digest.digest(str.getBytes("UTF-8"));
			return bts;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static byte[] encryptBy3DesECB(byte[] keyStr, byte[] src) {
		if(null == keyStr || null == src){
			return null;
		}
		try {
			
			SecretKey deskey = new SecretKeySpec(keyStr, "DESede");
			
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
		}
		return null;
	}

	public static byte[] encryptBy3DesCBC(byte[] keyStr, byte[] src) {
		if(null == keyStr || null == src){
			return null;
		}
		try {
			
			SecretKey deskey = new SecretKeySpec(keyStr, "DESede");
			
			Cipher c1 = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			IvParameterSpec spec = new IvParameterSpec("12345678".getBytes());
			c1.init(Cipher.ENCRYPT_MODE, deskey, spec);
			return c1.doFinal(src);// 
		} catch (Exception e) {
		}
		return null;
	}

	public static byte[] decryptBy3DesECB(byte[] keybyte, byte[] src) {
		if(null == keybyte || null == src){
			return null;
		}
		try {
			
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
			
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
		}
		return null;
	}

	public static byte[] decryptBy3DesCBC(byte[] keybyte, byte[] src) {
		if(null == keybyte || null == src){
			return null;
		}
		try {
			
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
			
			Cipher c1 = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			IvParameterSpec spec = new IvParameterSpec("12345678".getBytes());
			c1.init(Cipher.DECRYPT_MODE, deskey, spec);
			byte[] test = c1.doFinal(src);
			//return c1.doFinal(src);
			SysLog.println("test = " + test.length);
			return test;
		} catch (Exception e) {
			//e.printStackTrace();
			SysLog.println("e = " + e.getLocalizedMessage());
			
		
		}
		
		return null;
	}
	
	public static String encode2Base64(byte[] bytes) {
		if(null == bytes){
			return null;
		}
		byte[] bts = Base64.encode(bytes);
		return new String(bts);
	}

	public static byte[] decode2Base64(String str) {
		if(null == str){
			return null;
		}
		byte[] bts = Base64.decode(str);
		return bts;
	}
}
