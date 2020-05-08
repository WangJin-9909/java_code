package utils;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;

public class RSAUtils {
	public static void main(String[] args) {
		
		RSAPrivateCrtKey rsaPrivateKey = getRSAPrivateKey("D:\\test.keystore", "yiersansi", "test1", "yiersansi");
		BigInteger modulus = rsaPrivateKey.getModulus();
		System.out.println("module = " + modulus.toString(16).length());
		
		
		
		RSAPublicKey rsaPublicKey = getRSAPublicKey("D:\\test.keystore", "yiersansi", "test1");
		BigInteger modulusPub = rsaPublicKey.getModulus();
		System.out.println("modulusPub = " + modulusPub.toString(16).length());
	}

	public static RSAPublicKey getRSAPublicKey(String keyStoreFile, String storeFilePass, String keyAlias) {
		return (RSAPublicKey) JKSUtil.getPublicKey(keyStoreFile, storeFilePass, keyAlias);
	}

	public static RSAPrivateCrtKey getRSAPrivateKey(String keyStoreFile, String storeFilePass, String keyAlias,
			String keyAliasPass) {
		return (RSAPrivateCrtKey) JKSUtil.getPrivateKey(keyStoreFile, storeFilePass, keyAlias, keyAliasPass);
	}

}
