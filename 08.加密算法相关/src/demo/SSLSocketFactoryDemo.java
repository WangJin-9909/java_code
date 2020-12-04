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

			// 启用密码组
			String[] supportedCipherSuites = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supportedCipherSuites);

			System.out.println("--------------------打印密码组----------------------------");
			for(int i = 0; i < supportedCipherSuites.length; i++) {
				System.out.println("i =  " + supportedCipherSuites[i]);
			}
			
			
			
			Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

			// https在get中需要完全的URL
			out.write("GET https://" + host + "/ HTTP/1.1\r\n");
			out.write("Host:" + host + "\r\n");
			out.write("\r\n");
			out.flush();

			// 读取相应

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// 读取首部
			System.out.println("------------------首部-------------------");
			String s;
			while (!(s = reader.readLine()).equals("")) {
				System.out.println(s);
			}
			System.out.println();

			// 读取长度
			String contentLength = reader.readLine();
			int length = Integer.MAX_VALUE;
			try {
				length = Integer.parseInt(contentLength.trim(), 16);
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				// 这个服务器在响应题的第一行没有发送content-length
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
