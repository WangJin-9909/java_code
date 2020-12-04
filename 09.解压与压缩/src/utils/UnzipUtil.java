package utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class UnzipUtil {

	// 解压.zip文件
	public static void unZip(String sourceFile, String outputDir) throws IOException {
		ZipFile zipFile = null;
		File file = new File(sourceFile);
		try {
			Charset CP866 = Charset.forName("CP866"); // specifying alternative (non UTF-8) charset
			zipFile = new ZipFile(file, CP866);
			createDirectory(outputDir, null);// 创建输出目录

			Enumeration<?> enums = zipFile.entries();
			while (enums.hasMoreElements()) {

				ZipEntry entry = (ZipEntry) enums.nextElement();
				System.out.println("解压 文件名 = " + entry.getName());
				if(!entry.getName().contains("import"))
					continue;

				if (entry.isDirectory()) {// 是目录
					createDirectory(outputDir, entry.getName());// 创建空目录
				} else {// 是文件
					File tmpFile = new File(outputDir + "/" + entry.getName());
					createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录

					InputStream in = null;
					OutputStream out = null;
					try {
						in = zipFile.getInputStream(entry);
						;
						out = new FileOutputStream(tmpFile);
						int length = 0;

						byte[] b = new byte[2048];
						while ((length = in.read(b)) != -1) {
							out.write(b, 0, length);
						}

					} catch (IOException ex) {
						throw ex;
					} finally {
						if (in != null)
							in.close();
						if (out != null)
							out.close();
					}
				}
			}

		} catch (IOException e) {
			throw new IOException("解压缩文件出现异常", e);
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException ex) {
				throw new IOException("关闭zipFile出现异常", ex);
			}
		}
	}

	/**
	 * 构建目录
	 * 
	 * @param outputDir
	 * @param subDir
	 */
	public static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		if (!(subDir == null || subDir.trim().equals(""))) {// 子目录不为空
			file = new File(outputDir + "/" + subDir);
		}
		if (!file.exists()) {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			file.mkdirs();
		}
	}

	// 解压.gz文件
	public static void unGz(String sourceFile, String outputDir) {
		String ouputfile = "";
		try {
			// 建立gzip压缩文件输入流
			FileInputStream fin = new FileInputStream(sourceFile);
			// 建立gzip解压工作流
			GZIPInputStream gzin = new GZIPInputStream(fin);
			// 建立解压文件输出流
			/*
			 * ouputfile = sourceFile.substring(0,sourceFile.lastIndexOf('.')); ouputfile =
			 * ouputfile.substring(0,ouputfile.lastIndexOf('.'));
			 */
			File file = new File(sourceFile);
			String fileName = file.getName();
			outputDir = outputDir + "/" + fileName.substring(0, fileName.lastIndexOf('.'));
			FileOutputStream fout = new FileOutputStream(outputDir);

			int num;
			byte[] buf = new byte[1024];

			while ((num = gzin.read(buf, 0, buf.length)) != -1) {
				fout.write(buf, 0, num);
			}

			gzin.close();
			fout.close();
			fin.close();
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return;
	}

	
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	
	public static void main(String[] args) throws Exception {
		// 测试解压文件（1. .zip 2. .rar 3. .gz 4. .tar.gz）

		String zipPath = "D:\\Users\\wangJin\\Downloads\\WeChat Files\\zisebangbangtang\\FileStorage\\File\\2020-10\\模拟练习试卷\\";
		String outputDir = "D:\\Users\\wangJin\\Downloads\\WeChat Files\\zisebangbangtang\\FileStorage\\File\\2020-10\\模拟练习试卷\\dst";

		// 遍历目录下的文件

		File[] listFile = listFile(zipPath);
		for (int i = 0; i < listFile.length; i++) {
			String src = listFile[i].getAbsolutePath();
			String dist = listFile[i].getName();
			System.out.println("dist = "+ dist);
			String distFolder = "D:\\Users\\wangJin\\Downloads\\WeChat Files\\zisebangbangtang\\FileStorage\\File\\2020-10\\模拟练习试卷\\dst";// + getFileNameNoEx(dist);
			System.out.println("src = " + src + "    distFolder = " + distFolder);
			// 解压文件
			 unZip(src, distFolder);
		}
		// 传入参数（待解压的压缩文件路径, 解压文件到的目标文件夹）
		
		

	}

	
	public static File[] listFile(String path) {
		File file = new File(path); // 获取其file对象
		File[] fs = file.listFiles(); // 遍历path下的文件和目录，放在File数组中
		for (File f : fs) { // 遍历File[]数组
			if (!f.isDirectory()) // 若非目录(即文件)，则打印
				System.out.println("");
		}
		return fs;

	}


	public static void folderMethod1(String path) {
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
		LinkedList<File> list = new LinkedList<>();

		if (file.exists()) {
			if (null == file.listFiles()) {
				return;
			}
			list.addAll(Arrays.asList(file.listFiles()));
			while (!list.isEmpty()) {
				File[] files = list.removeFirst().listFiles();
				if (null == files) {
					continue;
				}
				for (File f : files) {
					if (f.isDirectory()) {
						System.out.println("文件夹:" + f.getAbsolutePath());
						list.add(f);
						folderNum++;
					} else {
						System.out.println("文件:" + f.getAbsolutePath());
						fileNum++;
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		System.out.println("文件夹数量:" + folderNum + ",文件数量:" + fileNum);
	}

}