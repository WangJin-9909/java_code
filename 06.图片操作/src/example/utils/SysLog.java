/**
* @author Dabing
* @E-mail chenbinging@outlook.com
* @date 2019-4-18下午3:07:02
* 系统日志打印
*/
package example.utils;

public class SysLog {

	private static int c = 1;
	
	public static void logCon (int c) {
		SysLog.c = c;
	}
	public static void println(String s) {
		if(c == 1) {
			System.out.println(s);
		}
	}
}
