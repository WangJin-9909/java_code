package my;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SwingDemo {
	private static void createGUI() {
		JFrame frame = new MyFrame("移动端安全键盘验证工具");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 450);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});

	}
}
