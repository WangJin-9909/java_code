package example;

public class CutRect {
	public static final int width = 300;
	public static final int height = 200;

	public int sx1;
	public int sy1;
	public int sx2;
	public int sy2;

	/**
	 * ����һ�η������ɾ������꣬�����������width��height��Χ�ڣ���û�м�϶
	 */
	public CutRect generate() {
		CutRect cr = new CutRect();

		return cr;
	}

	public void random(CutRect rect) {
		this.sx1 = rect.sx1 / 2;
		this.sy1 = rect.sy1 / 2;
	}
	
	
	public static void main(String[] args) {
		CutRect cr = new CutRect();
		cr.random(cr);
	}

}
