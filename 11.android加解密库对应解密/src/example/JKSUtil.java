package example;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * 
* @author Dabing
* @E-mail chenbinging@outlook.com
* @date 2016-12-23涓婂崍10:57:14
* 璇诲彇keystore鏂囦欢
 */
public class JKSUtil {
	public static PublicKey getPublicKey(String keyStoreFile,
			String storeFilePass, String keyAlias) {

		//读取密钥是所要用到的工具类
		KeyStore ks;

		//公钥类对应的类
		PublicKey pubkey = null;
		try {

			//得到实体对象 
			ks = KeyStore.getInstance("JKS");
			FileInputStream fin;
			try {

				//读取JKS文件
				fin = new FileInputStream(keyStoreFile);
				try {
					//读取公钥
					ks.load(fin, storeFilePass.toCharArray());
					java.security.cert.Certificate cert = ks
							.getCertificate(keyAlias);
					pubkey = cert.getPublicKey();
					fin.close();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (CertificateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return pubkey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param keyStoreFile
	 *            私钥文件
	 * @param storeFilePass
	 *            私钥文件密码
	 * @param keyAlias
	 *            别名
	 * @param keyAliasPass
	 *            密码
	 * @return
	 */
	public static PrivateKey getPrivateKey(String keyStoreFile,
			String storeFilePass, String keyAlias, String keyAliasPass) {
		KeyStore ks;
		PrivateKey prikey = null;
		try {
			ks = KeyStore.getInstance("JKS");
			FileInputStream fin;
			try {
				fin = new FileInputStream(keyStoreFile);
				try {
					try {
						ks.load(fin, storeFilePass.toCharArray());
						// 先打开文件
						prikey = (PrivateKey) ks.getKey(keyAlias,
								keyAliasPass.toCharArray());
						fin.close();
						//通过别名和密码得到私钥
					} catch (UnrecoverableKeyException e) {
						e.printStackTrace();
					} catch (CertificateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return prikey;
	}
}
