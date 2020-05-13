package example;

public class Test {
	public static void main(String[] args) {
		intArrToStr();
	}

	// Êý×é×ª×Ö·û´®
	public static String intArrToStr() {
		int[] arr = { 1, 5, 3, 4, 0, 2 };
		String str = "";
		for (int i = 0; i < arr.length; i++) {
			str += arr[i];
		}
		return str;
		//System.out.println("str = " + str); // abc
	}
}
