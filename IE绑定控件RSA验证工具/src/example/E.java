package example;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import example.JKSUtil;

public class E {

	public static void main(String[] args) {
		printPubM();
	}
	
	public static void t() {
		String filePath = "D:\\ICBC\\RSAKey.keystore";
		String jksPass = "#VFR4cde";
		String keyAlias = "RSAKey";

		RSAPublicKey publicKey = (RSAPublicKey) JKSUtil.getPublicKey(filePath, jksPass, keyAlias);

		String pubKeyString = publicKey.getModulus().toString(16);
		System.out.println("pubKeyString = " + pubKeyString);
		System.out.println("pubKeyString = " + pubKeyString.length());

	}

	public static void printPubM() {
		// TODO Auto-generated method stub
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\test.jks", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\test.jks", "yiersansi", "test1");
		// 公私钥转换
		RSAPublicKey rpub = (RSAPublicKey) publicKey;
		RSAPrivateKey rpri = (RSAPrivateKey) privateKey;
		// 打印公钥
		System.out.println("公钥M是 ： " + rpub.getModulus().toString(16));

		System.out.println("公钥E是  ：" + new String(Base64.getEncoder().encode((publicKey.getEncoded()))));
		// 打印私钥
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
	}

}
