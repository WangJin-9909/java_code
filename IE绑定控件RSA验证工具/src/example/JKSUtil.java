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

		// 璇诲彇瀵嗛挜鏄墍瑕佺敤鍒扮殑宸ュ叿绫�
		KeyStore ks;

		// 鍏挜绫绘墍瀵瑰簲鐨勭被
		PublicKey pubkey = null;
		try {

			// 寰楀埌瀹炰緥瀵硅薄
			ks = KeyStore.getInstance("JKS");
			FileInputStream fin;
			try {

				// 璇诲彇JKS鏂囦欢
				fin = new FileInputStream(keyStoreFile);
				try {
					// 璇诲彇鍏挜
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
	 * 寰楀埌绉侀挜
	 * 
	 * @param keyStoreFile
	 *            绉侀挜鏂囦欢
	 * @param storeFilePass
	 *            绉侀挜鏂囦欢鐨勫瘑鐮�
	 * @param keyAlias
	 *            鍒悕
	 * @param keyAliasPass
	 *            瀵嗙爜
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
						// 鍏堟墦寮�鏂囦欢
						prikey = (PrivateKey) ks.getKey(keyAlias,
								keyAliasPass.toCharArray());
						fin.close();
						// 閫氳繃鍒悕鍜屽瘑鐮佸緱鍒扮閽�
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
