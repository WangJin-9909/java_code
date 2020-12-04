package cn.com.icbc.ms.token.utils;

public class SysLog {
	public static final boolean isDebug = false;
	public static void println(String str){
		if(null == str) {
			System.out.print("");
		}
		if(isDebug){
			System.out.println("+++DEBUG+++" + str);
		}
	}
}
