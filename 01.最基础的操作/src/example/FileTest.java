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
	 * 读取目录下的所有文件
	 * 
	 * @param dir       目录
	 * @param fileNames 保存文件名的集合
	 * @return
	 */
	public static void findFileList(String path) {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
			return;
		}
		String[] files = dir.list();// 读取目录下的所有目录文件信息
		for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
			File file = new File(dir, files[i]);
			if (file.isFile()) {// 如果文件
				System.out.println("file = " + file);
			} else {// 如果是目录

			}
		}
	}

	public static void readFile(String path) throws IOException {
		try (FileReader reader = new FileReader(path); BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
