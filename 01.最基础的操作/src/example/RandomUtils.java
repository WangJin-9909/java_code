package example;

import java.util.Random;

public class RandomUtils {
	public static void main(String[] args) {
		int[] generateRandom = generateRandom(26);
		for(int i = 0; i < generateRandom.length; i++) {
			System.out.println("i = " + i + "random = " + generateRandom[i]);
		}
	}

	/**
	 * ����maxValue����ֵ
	 * @param maxValue
	 * @return
	 */
	public static int[] generateRandom(int maxValue) {
		int[] res = new int[maxValue];
		Random random = new Random();
		boolean[] bool = new boolean[maxValue * 2];
		int randInt = 0;
		for (int j = 0; j < maxValue; j++) {
			/** �õ�maxValue����ͬ������� */
			do {
				randInt = random.nextInt(maxValue);
			} while (bool[randInt]);
			bool[randInt] = true;

			res[j] = randInt;
		}
		return res;

	}

}
