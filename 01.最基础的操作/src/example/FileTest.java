package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileTest {

	public static void main(String[] args) throws IOException {
		// findFileList("D:\\");
		while (true) {
			readFile("D:\\03.workspace\\01.server\\TestHttpProtocolNew\\demo.jpeg");
		}

	}

	/**
	 * ��ȡĿ¼�µ������ļ�
	 * 
	 * @param dir       Ŀ¼
	 * @param fileNames �����ļ����ļ���
	 * @return
	 */
	public static void findFileList(String path) {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {// �ж��Ƿ����Ŀ¼
			return;
		}
		String[] files = dir.list();// ��ȡĿ¼�µ�����Ŀ¼�ļ���Ϣ
		for (int i = 0; i < files.length; i++) {// ѭ��������ļ�����ص�����
			File file = new File(dir, files[i]);
			if (file.isFile()) {// ����ļ�
				System.out.println("file = " + file);
			} else {// �����Ŀ¼

			}
		}
	}

	public static void readFile(String path) throws IOException {
		try (FileReader reader = new FileReader(path); BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				// һ�ζ���һ������
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
