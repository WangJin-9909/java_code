package com.ms.icbc.security.utils;

public class SysLog {
	public static final boolean isDebug = true;
	public static void println(String str){
		if(null == str) {
			System.out.print("");
		}
		if(isDebug){
			System.out.println("+++DEBUG+++" + str);
		}
	}
}
