package demo;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class SSLSocketFactoryDemo {

	public static void main(String[] args) {
		int port = 443;
		String host = "sp0.baidu.com";

		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try {
			socket = (SSLSocket) factory.createSocket(host, port);

			// ����������
			String[] supportedCipherSuites = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supportedCipherSuites);

			System.out.println("--------------------��ӡ������----------------------------");
			for(int i = 0; i < supportedCipherSuites.length; i++) {
				System.out.println("i =  " + supportedCipherSuites[i]);
			}
			
			
			
			Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

			// https��get����Ҫ��ȫ��URL
			out.write("GET https://" + host + "/ HTTP/1.1\r\n");
			out.write("Host:" + host + "\r\n");
			out.write("\r\n");
			out.flush();

			// ��ȡ��Ӧ

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// ��ȡ�ײ�
			System.out.println("------------------�ײ�-------------------");
			String s;
			while (!(s = reader.readLine()).equals("")) {
				System.out.println(s);
			}
			System.out.println();

			// ��ȡ����
			String contentLength = reader.readLine();
			int length = Integer.MAX_VALUE;
			try {
				length = Integer.parseInt(contentLength.trim(), 16);
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				// �������������Ӧ��ĵ�һ��û�з���content-length
			}

			int c;
			int i = 0;

			while ((c = reader.read()) != -1 && i++ < length) {
				System.out.write(c);
			}

			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
