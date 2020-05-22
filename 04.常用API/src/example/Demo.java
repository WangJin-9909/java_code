package example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {
	public static void main(String[] args) {
		 
		String str = getCurTime();
		System.out.println("str = " + str);
	}
	
	
	public static String getCurTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		return df.format(new Date());
	}
}
