package example;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 验证前请将test.jks复制到D盘
 * 
 * @author wangJin
 *
 */
public class RsaDecrypt {
	public static void main(String[] args) throws Exception {
		decrypt();
	}

	/**
	 * 解密android通用加解密库的rsa密文
	 * 
	 * @throws Exception
	 */
	private static void decrypt() throws Exception {
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa2048.jks", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\rsa2048.jks", "yiersansi", "test1");
		// 公私钥转换
		RSAPublicKey rpub = (RSAPublicKey) publicKey;
		RSAPrivateKey rpri = (RSAPrivateKey) privateKey;

		//打印公钥，将公钥M复制到android对应的工程
		System.out.println("公钥M是 ： " + rpub.getModulus().toString(16));
		System.out.println("公钥E是  ：" + new String(Base64.getEncoder().encode((publicKey.getEncoded()))));
		// 打印私钥
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
		//------------------------------------------------------------------------------------------------------------------------------------------
		// 将android生成的Log密文，复制到下面
		String str = "9A2ED98A3DF75FFF37C534263B7E4E69673B22ADFA201B38553CEE873D404CA218273A382A1220C1B017F653CED7BB36411F03F58352D29E46725A4F1442A45D20044705D64E113A41E41A8AB57DA218FE9E64ADEB8CDBE22DC9CD366901B40325FD0D09F1A1DAEA5A8FF42DD6720A51505931673253FB16B6759F2EF05FB4689E0E331A306326CA2F9BE759E15918A116A92E8D54E11A365338CB3C0A0056B7225DA37A96AC63C2A0E7492A67EFCD8C1006D0E5FDEBA286FCAF0AEF8EF35816A76E26DD3136CC0C960D105C8A5A8D946DC012B55EC66A1FEF75F7294238C0CE29965BE47EACE1DC3F9881F1C25B9924D0BE3A83611F7B2428B2CB20B6BB8003";
		String decryptByPrivateKey = decryptByPrivateKey(str, rpri);
		System.out.println("android端加密明文是= " + decryptByPrivateKey);

	}

	public static String base64Filter(String enStr, boolean isRsa1024) {
		byte[] des;
		if (isRsa1024) {
			des = new byte[256];
		} else {
			des = new byte[512];
		}
		byte[] decode = Base64.getDecoder().decode(enStr);
		System.out.println("decode  =  " + new String(decode));
		byte b = decode[1];
		for (int i = 0, j = 0; i < decode.length; i++) {
			if (b == decode[i]) {
				continue;
			}
			des[j++] = decode[i];
		}
		return new String(des);
	}

	public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		// System.err.println(bcd.length);
		// 如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for (byte[] arr : arrays) {
			ming += new String(cipher.doFinal(arr));
		}
		return ming;
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		// 如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}

	/**
	 * BCD转字符串
	 */
	public static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	/**
	 * 拆分字符串
	 */
	public static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i = 0; i < x + z; i++) {
			if (i == x + z - 1 && y != 0) {
				str = string.substring(i * len, i * len + y);
			} else {
				str = string.substring(i * len, i * len + len);
			}
			strings[i] = str;
		}
		return strings;
	}

	/**
	 * ASCII码转BCD码
	 * 
	 */
	public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	/**
	 * 拆分数组
	 */
	public static byte[][] splitArray(byte[] data, int len) {
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x + z][];
		byte[] arr;
		for (int i = 0; i < x + z; i++) {
			arr = new byte[len];
			if (i == x + z - 1 && y != 0) {
				System.arraycopy(data, i * len, arr, 0, y);
			} else {
				System.arraycopy(data, i * len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	public static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}

	
}
