package example;

public class StringTest {
	static final String NO_SIGNAL = null;
	public static void main(String[] args) {
		System.out.println("+++++++++++++++++++++++++++++++++");
		//�ж��ַ������,���Ѻõ��ַ����жϷ�ʽ��
		LogUtils.println("nosignal".equals(NO_SIGNAL));
		//����ķ�ʽ���׵��³����쳣����
		LogUtils.print(NO_SIGNAL.equals("nosignal"));
	}

	
	
	
	
	
	
	
	
}
