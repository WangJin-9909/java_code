package example;

import java.util.Random;

public class Test {
	public static void main(String[] args) {
		int[] is = generateRandom(6);
		for (int i = 0; i < is.length; i++) {
			//System.out.print("i  = " + is[i]);
		}
		System.out.println("--------------------------");
		for (int i = 0; i < 10; i++) {
			getRandominRange();
		}

	}

	/**
	 * ��������������ظ���Ŀǰ����maxValue���Ϊ10
	 * 
	 * @param maxValue
	 */
	public static int[] generateRandom(int maxValue) {
		int[] res = new int[maxValue];
		Random random = new Random();
		boolean[] bool = new boolean[maxValue * 2];
		int randInt = 0;
		for (int j = 0; j < maxValue; j++) {
			/** �õ�10����ͬ������� */
			do {
				randInt = random.nextInt(maxValue);
			} while (bool[randInt]);
			bool[randInt] = true;
			// System.out.println(randInt);
			res[j] = randInt;
		}
		return res;

	}

	public static void getRandominRange() {
		int i = new Random().nextInt(4) + 2;
		System.out.println(" i  =  " + i);
	}

}
