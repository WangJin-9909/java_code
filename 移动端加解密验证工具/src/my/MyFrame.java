package my;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.icbc.ms.component.CommonDecryptUtil;

import af.swing.layout.AfSimpleLayout;

public class MyFrame extends JFrame {
	JLabel rule = new JLabel("规则一[rule]:");
	JTextField rule_edittext = new JTextField(16);
	JLabel change_rule = new JLabel("规则二[change_rule]:");
	JTextField change_rule_edittext = new JTextField(16);
	JLabel password = new JLabel("密文[password]:");
	JTextField password_edittext = new JTextField(16);
	JComboBox<String> encryptMethodList = new JComboBox();
	JButton btn_decode = new JButton("解     密");
	JButton btn_choose_ini_file = new JButton("选择Ini文件");
	JLabel ini_file = new JLabel("");
	JTextField result_edittext = new JTextField(22);
	JLabel passeord_label = new JLabel("密码明文：");
	Container root;
	File mIniFile;
	// 加密算法类型 0,1:rsa1024, 2:rsa2048, 3:SM2.
	static int ENCODE_METHOD = 1;
	private static final int RSA1024_OLD = 0;
	private static final int RSA1024_NEW = 1;
	private static final int RSA2048 = 2;
	private static final int SM2 = 3;

	private static final boolean DEBUG = true;

	public MyFrame(String title) {
		super(title);
		root = this.getContentPane();
		LayoutManager layoutMgr = new MyCustomLayout();
		root.setLayout(layoutMgr);

		initList();
		initListener();
		initViews();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		// 密文
		password.setBounds(30, 40, 200, 35);
		root.add(password);
		password_edittext.setBounds(240, 40, 300, 35);
		root.add(password_edittext);

		// 规则一
		rule.setBounds(30, 80, 200, 35);
		root.add(rule);
		rule_edittext.setBounds(240, 80, 300, 35);
		root.add(rule_edittext);

		// 规则二
		change_rule.setBounds(30, 120, 200, 35);
		root.add(change_rule);
		change_rule_edittext.setBounds(240, 120, 300, 35);
		root.add(change_rule_edittext);
		// 解密方式
		encryptMethodList.setBounds(30, 180, 130, 35);
		root.add(encryptMethodList);
		// 选择INI文件
		btn_choose_ini_file.setBounds(240, 180, 100, 30);
		root.add(btn_choose_ini_file);

		// INI文件标识
		ini_file.setBounds(360, 180, 300, 35);
		ini_file.setText("Ini文件未选择：");
		root.add(ini_file);
		// 解密Button
		btn_decode.setBounds(200, 240, 150, 30);
		btn_decode.setForeground(Color.RED);
		root.add(btn_decode);

		//标识密码
		passeord_label.setBounds(50 + 120, 320, 150, 35);
		root.add(passeord_label);

		//最终显示密码的EditText
		result_edittext.setBounds(150 + 120, 320, 150, 35);
		result_edittext.setText("解密后从这里复制明文");
		root.add(result_edittext);

		

	}

	private void initListener() {
		// TODO Auto-generated method stub
		// 下拉列表的事件处理
		encryptMethodList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ENCODE_METHOD = encryptMethodList.getSelectedIndex();
				if (DEBUG) {
					System.out.println("选择了加密方法： "
							+ encryptMethodList.getSelectedIndex());
				}
			}
		});
		// 按钮点击处理
		btn_decode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// rule
				String rule = rule_edittext.getText();
				// changeRule
				String change_rule = rule_edittext.getText();
				// password
				String password = password_edittext.getText();
				if (rule.equals("")) {
					JOptionPane.showMessageDialog(MyFrame.this, "非法字段输入:!!!");
					return;
				}

				if (change_rule.equals("")) {
					JOptionPane.showMessageDialog(MyFrame.this, "非法字段输入:!!!");
					return;
				}

				if (password.equals("")) {
					JOptionPane.showMessageDialog(MyFrame.this, "非法字段输入:!!!");
					return;
				}

				// 计算明文
				String str = get_user_password(ENCODE_METHOD);
				if (str.equals("error")) {
					showMessage("计算出错！！检查[rule]  [change_rule]  [password]");
					return;
				}
				if (str.equals("")) {
					return;
				}
				result_edittext.setText(str);
				// 消息提示框 ( 注意 showMessageDialog() 是静态方法 )
				// JOptionPane.showMessageDialog(MyFrame.this, "密码: " + str);
			}

		});
		// 选择文件
		btn_choose_ini_file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					showMessage("不能选择文件夹");
					return;
				} else if (file.isFile()) {
					System.out.println("文件:" + file.getAbsolutePath());
				}
				System.out.println(jfc.getSelectedFile().getName());
				mIniFile = file;
				ini_file.setText("ini文件     :          " + mIniFile.getName());
			}
		});
	}

	private String get_user_password(int method) {
		if (null == mIniFile) {
			showMessage("选择INI文件");
			return "";
		}

		try {
			/**
			 * 参数1：0【老版本RSA】 1【新版本RSA】 2：RSA2048 3:SM2
			 * 
			 * 解密参数："D:\\dataST1.ini"
			 */
			CommonDecryptUtil util = new CommonDecryptUtil(3, 20000,
					mIniFile.getAbsolutePath(), "");
			String a = util.getRandomCode();
			String ver = util.getVersion();
			String random;
			try {
				random = util.getRandomCode();
				String encryptCode = password_edittext.getText();
				String rule = rule_edittext.getText();
				String change_rule = change_rule_edittext.getText();
				if (encryptCode.equals("") || rule.equals("")
						|| change_rule.equals("")) {
					showMessage("输入参数错误！！！");
				}
				if (DEBUG) {
					System.out.println("rule:      " + rule);
					System.out.println("change_rule:     " + change_rule);
					System.out.println("password:     " + password);
					System.out.println("加密方法:     " + ENCODE_METHOD);

				}
				StringBuilder sb = new StringBuilder();
				/**
				 * 参数1：密文数据 客户端传上来的rule 客户端传上来得changeRule 存放数据
				 */
				util.symmetricDecrypt(encryptCode, rule, change_rule, sb);

				System.out.println("明文是：" + sb.toString());
				return sb.toString();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块

				e.printStackTrace();
				return "error";
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块

			e.printStackTrace();
			return "error";
		}

	}

	private void showMessage(String str) {
		// 消息提示框 ( 注意 showMessageDialog() 是静态方法 )
		JOptionPane.showMessageDialog(MyFrame.this, "错误: " + str);
	}

	private void initList() {
		// TODO Auto-generated method stub
		encryptMethodList.addItem("RSA1024_老版本");
		encryptMethodList.addItem("RSA1024_新版本");
		encryptMethodList.addItem("RSA2048");
		encryptMethodList.addItem("SM2");

	}

	// ColorfulLabel: 参考4.5节的讲解
	private static class ColorfulLabel extends JLabel {
		public ColorfulLabel(String text, Color bgColor) {
			super(text);
			setOpaque(true);
			setBackground(bgColor);
			// setPreferredSize(new Dimension(60,30));
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(new Font("宋体", Font.PLAIN, 16));
		}
	}

	private class MyCustomLayout extends AfSimpleLayout {

		@Override
		public void layoutContainer(Container parent) {
			int w = parent.getWidth(); // 父窗口的宽度 width
			int h = parent.getHeight(); // 父窗口的高度 height
			System.out.println("父容器: " + w + ", " + h);

		}

	}

}
