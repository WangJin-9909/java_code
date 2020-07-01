package example;

public class StringTest {
	static final String NO_SIGNAL = null;
	public static void main(String[] args) {
		System.out.println("+++++++++++++++++++++++++++++++++");
		//判断字符串相等,更友好的字符串判断方式，
		LogUtils.println("nosignal".equals(NO_SIGNAL));
		//下面的方式容易导致程序异常崩溃
		LogUtils.print(NO_SIGNAL.equals("nosignal"));
	}

	
	
	
	
	
	
	
	
}
