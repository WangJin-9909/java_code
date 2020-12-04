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

	// ��ѹ.zip�ļ�
	public static void unZip(String sourceFile, String outputDir) throws IOException {
		ZipFile zipFile = null;
		File file = new File(sourceFile);
		try {
			Charset CP866 = Charset.forName("CP866"); // specifying alternative (non UTF-8) charset
			zipFile = new ZipFile(file, CP866);
			createDirectory(outputDir, null);// �������Ŀ¼

			Enumeration<?> enums = zipFile.entries();
			while (enums.hasMoreElements()) {

				ZipEntry entry = (ZipEntry) enums.nextElement();
				System.out.println("��ѹ �ļ��� = " + entry.getName());
				if(!entry.getName().contains("import"))
					continue;

				if (entry.isDirectory()) {// ��Ŀ¼
					createDirectory(outputDir, entry.getName());// ������Ŀ¼
				} else {// ���ļ�
					File tmpFile = new File(outputDir + "/" + entry.getName());
					createDirectory(tmpFile.getParent() + "/", null);// �������Ŀ¼

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
			throw new IOException("��ѹ���ļ������쳣", e);
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException ex) {
				throw new IOException("�ر�zipFile�����쳣", ex);
			}
		}
	}

	/**
	 * ����Ŀ¼
	 * 
	 * @param outputDir
	 * @param subDir
	 */
	public static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		if (!(subDir == null || subDir.trim().equals(""))) {// ��Ŀ¼��Ϊ��
			file = new File(outputDir + "/" + subDir);
		}
		if (!file.exists()) {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			file.mkdirs();
		}
	}

	// ��ѹ.gz�ļ�
	public static void unGz(String sourceFile, String outputDir) {
		String ouputfile = "";
		try {
			// ����gzipѹ���ļ�������
			FileInputStream fin = new FileInputStream(sourceFile);
			// ����gzip��ѹ������
			GZIPInputStream gzin = new GZIPInputStream(fin);
			// ������ѹ�ļ������
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
		// ���Խ�ѹ�ļ���1. .zip 2. .rar 3. .gz 4. .tar.gz��

		String zipPath = "D:\\Users\\wangJin\\Downloads\\WeChat Files\\zisebangbangtang\\FileStorage\\File\\2020-10\\ģ����ϰ�Ծ�\\";
		String outputDir = "D:\\Users\\wangJin\\Downloads\\WeChat Files\\zisebangbangtang\\FileStorage\\File\\2020-10\\ģ����ϰ�Ծ�\\dst";

		// ����Ŀ¼�µ��ļ�

		File[] listFile = listFile(zipPath);
		for (int i = 0; i < listFile.length; i++) {
			String src = listFile[i].getAbsolutePath();
			String dist = listFile[i].getName();
			System.out.println("dist = "+ dist);
			String distFolder = "D:\\Users\\wangJin\\Downloads\\WeChat Files\\zisebangbangtang\\FileStorage\\File\\2020-10\\ģ����ϰ�Ծ�\\dst";// + getFileNameNoEx(dist);
			System.out.println("src = " + src + "    distFolder = " + distFolder);
			// ��ѹ�ļ�
			 unZip(src, distFolder);
		}
		// �������������ѹ��ѹ���ļ�·��, ��ѹ�ļ�����Ŀ���ļ��У�
		
		

	}

	
	public static File[] listFile(String path) {
		File file = new File(path); // ��ȡ��file����
		File[] fs = file.listFiles(); // ����path�µ��ļ���Ŀ¼������File������
		for (File f : fs) { // ����File[]����
			if (!f.isDirectory()) // ����Ŀ¼(���ļ�)�����ӡ
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
						System.out.println("�ļ���:" + f.getAbsolutePath());
						list.add(f);
						folderNum++;
					} else {
						System.out.println("�ļ�:" + f.getAbsolutePath());
						fileNum++;
					}
				}
			}
		} else {
			System.out.println("�ļ�������!");
		}
		System.out.println("�ļ�������:" + folderNum + ",�ļ�����:" + fileNum);
	}

}