package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileTest {

	public static void main(String[] args) throws IOException {
		//1.����·��
		//findFileList("D:\\");
		//2.����·��
		findFileList("https://129.28.184.240:8443/sensor_update/");
//		while (true) {
//			readFile("D:\\03.workspace\\01.server\\TestHttpProtocolNew\\demo.jpeg");
//		}
		
		

	}

	/**
	 * ��ȡĿ¼�µ������ļ�,����������ļ�
	 * 
	 * @param dir
	 * @return
	 */
	public static void findFileList(String filePath) {
		File dest = new File(filePath);

		// Ŀ¼
		if (dest.exists() || dest.isDirectory()) {
			String[] files = dest.list();// ��ȡĿ¼�µ�����Ŀ¼�ļ���Ϣ
			for (int i = 0; i < files.length; i++) {// ѭ��������ļ�����ص�����
				File file = new File(dest, files[i]);
				if (file.isFile()) {// ����ļ�
					System.out.println("file = " + file);
				} else {// �����Ŀ¼

				}
			}
		} else if (dest.exists() || !dest.isDirectory()) {

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
