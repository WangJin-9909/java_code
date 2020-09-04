package example;

public class PITest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(rand_pi(10000000)); // 改变参数值
	}

	public static double rand_pi(int n) {
		int numInCircle = 0;
		double x, y;
		double pi;
		for (int i = 0; i < n; i++) {
			x = Math.random();
			y = Math.random();
			if (x * x + y * y < 1)
				numInCircle++;
		}
		pi = (4.0 * numInCircle) / n;
		return pi;
	}

}
