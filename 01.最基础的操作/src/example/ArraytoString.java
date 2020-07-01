package example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数组字符串之间相互转换
 * @author wangJin
 *
 */
public class ArraytoString {
	public static void main(String[] args) {
		// intArrToStr();
		test1();
	}

	// 数组转字符串
	public static String intArrToStr() {
		int[] arr = { 1, 5, 3, 4, 0, 2 };
		String str = "";
		for (int i = 0; i < arr.length; i++) {
			str += arr[i];
		}
		return str;
		// System.out.println("str = " + str); // abc
	}

	public static void test1() {
		Date date = new Date();
		System.out.println(date.getTime());
		String str = "yyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		System.out.println(sdf.format(date));

	}
}
