package example;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncodedDemo {
	public static void main(String[] args) throws UnsupportedEncodingException {
		//������ݵ��ַ����а�������ŷ�ַ����ַ������ᱻת����%XX%XX  XXΪʮ�����Ƶ�����
		String str = "һ";//�������";
		String urlString = URLEncoder.encode(str, "UTF-8"); 
		System.out.println(urlString);  
		
		
		System.out.println("����󳤶ȣ� " +  urlString.length());  
		
		
		//-
		//������������ַ������н���
		String urlString1 = "%E4%B8%80%E4%BA%8C%E4%B8%89%E5%9B%9B%E4%BA%94%E5%85%AD%E4%B8%83%E5%85%AB%E4%B9%9D%E5%8D%81%E4%B8%80%E4%BA%8C%E4%B8%89%E5%9B%9B%E4%BA%94%E5%85%AD%E4%B8%83%E5%85%AB%E4%B9%9D%E5%8D%81%E4%B8%80%E4%BA%8C%E4%B8%89%E5%9B%9B%E4%BA%94%E5%85%AD";
		String keyWord = URLDecoder.decode(urlString1, "UTF-8");  
		System.out.println(keyWord);       
		 
	}

	
	
	 
	

}
