package example;

public class LogUtils {
	public static boolean DEBUG = true;

	public static void print(String str) {
		if (DEBUG) {
			System.out.print(str);
		}
	}
	public static void print(boolean str) {
		if (DEBUG) {
			System.out.print(str);
		}
	}

	public static void println(String str) {
		if (DEBUG) {
			System.out.println(str);
		}
	}
	public static void println(boolean str) {
		if (DEBUG) {
			System.out.println(str);
		}
	}
}
