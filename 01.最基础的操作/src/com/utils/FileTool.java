package com.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileTool {
	/**
	 * 使用传统io stream 下载文件
	 * 
	 * @param url
	 * @param saveDir
	 * @param fileName
	 */
	public static void download(String url, String saveDir, String fileName) {

		BufferedOutputStream bos = null;
		InputStream is = null;
		try {
			byte[] buff = new byte[8192];
			is = new URL(url).openStream();
			File file = new File(saveDir, fileName);
			file.getParentFile().mkdirs();
			bos = new BufferedOutputStream(new FileOutputStream(file));
			int count = 0;
			while ((count = is.read(buff)) != -1) {
				bos.write(buff, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 获取代码路径
	 */
	public static String  getCurrentDir() {
		String str = System.getProperty("user.dir");//user.dir指定了当前的路径
		System.out.println("dir = "+ str);
		return str;
	}
	
	/**
	 * 创建文件
	 * @param fileName
	 * @throws IOException
	 */
	public static void creatFile(String fileName) throws IOException {
		System.out.println("File.separator:" + File.separator);
		File testFile = new File("D:" + File.separator + File.separator + File.separator + fileName);
		File fileParent = testFile.getParentFile();
		String fileParentPath = testFile.getParent();
		System.out.println("fileParent:" + fileParent);
		System.out.println("fileParentPath:" + fileParentPath);
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}
		if (!testFile.exists())
			testFile.createNewFile();

	}
}
