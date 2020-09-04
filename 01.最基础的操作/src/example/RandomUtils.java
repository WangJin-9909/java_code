package example;

import java.util.Random;

public class RandomUtils {
	public static void main(String[] args) {
		int[] generateRandom = generateRandom(20);
		for (int i = 0; i < generateRandom.length; i++) {
			//System.out.println("i = " + i + "random = " + generateRandom[i]);
			System.out.print(numToAlpha(generateRandom[i]));
		}
	}

	/**
	 * 打乱maxValue个数值
	 * 
	 * @param maxValue
	 * @return
	 */
	public static int[] generateRandom(int maxValue) {
		int[] res = new int[maxValue];
		Random random = new Random();
		boolean[] bool = new boolean[maxValue * 2];
		int randInt = 0;
		for (int j = 0; j < maxValue; j++) {
			/** 得到maxValue个不同的随机数 */
			do {
				randInt = random.nextInt(maxValue);
			} while (bool[randInt]);
			bool[randInt] = true;

			res[j] = randInt;
		}
		return res;

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
