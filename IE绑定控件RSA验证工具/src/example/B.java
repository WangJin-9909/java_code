package example;

import java.io.File;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class B {
	public static void main(String[] args) throws Exception {
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa1024.jks", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\rsa1024.jks", "yiersansi", "test1");
		// 公私钥转换
		RSAPublicKey rpub = (RSAPublicKey) publicKey;
		System.out.println("公钥M是 ： " + rpub.getModulus().toString(16));
		
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
		RSAPrivateKey rpri = (RSAPrivateKey) privateKey;
		//String src = "QQBBADIAMQAzADEAQgBFAEIAQwAxADEARABEAEEARABFAEIAMABBADEARQBBAEUAQgBBADEARgBCAEUAMgA2ADQAQwBGAEUAQwAyADYANwBBAEMAQgAyADkAOAA2ADEAMAA3AEYARgA5AEQAMgBFADMARAAxADYAMQBGAEUANAA2ADcAMABGADUARABCADUAOAA2ADIARQA4ADEAMgA1ADIARgA5ADMAMQBCAEUAMgA0ADkARABCADcAMwBBAEIAQwBDAEYANgA4ADAARQA2AEIAOAA0AEEAMAA1ADYARAA5AEIAOQBDAEUAQQAyADUAOQBEADIAQwA0AEIANgA1ADQANQBEADgAOQA4ADkAMQA5AEUARAAyADIARQA3ADYARgAxADIAMAA3ADkAMwA5AEYANgAwADAAMgA0AEEARgA3AEMAMQA1ADIAMgAyADYAOAAyAEYAOQBFADIARQBBAEIARgA5ADkAQQBCADEAMwA5ADcAQwA2ADgANQBBADEANwBCADEARQBCADIANwA4ADIANAA3AEMANwAyADUAMwAyAEIANQAxADYAMwA0ADMAMQAwADMAQQA1ADQANwA2ADIANAA3ADgAMAA5AEIARQA3ADcAMQAwADcAQwBCADIARABDAEIAOAAxAEIANQBFAEEAMAA2ADYAQQAwAEYAOQA=";
		//byte[] decode = Base64.getDecoder().decode(src);
		//System.out.println("decode = " + decode.length);
		String de = "AA2131BEBC11DDADEB0A1EAEBA1FBE264CFEC267ACB2986107FF9D2E3D161FE4670F5DB5862E81252F931BE249DB73ABCCF680E6B84A056D9B9CEA259D2C4B6545D898919ED22E76F1207939F60024AF7C15222682F9E2EABF99AB1397C685A17B1EB278247C72532B516343103A5476247809BE77107CB2DCB81B5EA066A0F9";
		//System.out.println(decryptByPrivateKey(de, rpri));
		//---------------
		//System.out.println("decode = " + bytesToHexString(decode));
		//System.out.println("decode = " + decode.toString());
		// System.out.println("decode = " + new String(decode));
		//String str = "013B8580D18F8A74AB1CFDF0218F070EAF1F88F038D608AECD5D45632352DE7DDEE10876219BACD9A84C4A4E810FBC6DFDC643E4004569F90305A98FE3C8CD74ED8E9DCCD6F0F749389EBFAC14F1674540C2F4B533C4C81B243BB3DEBDB0FCB2DBDB0E90D2D553D8F624081E038CDCC4285B14DA7A9E1F9D42C54E97AB62F67C";
		//System.out.println(decryptByPrivateKey(str.getBytes(), rpri));
		//test1();
	}
	
	
	public static void test1()throws Exception{
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa1024.jks", "yiersansi", "test1", "yiersansi");RSAPrivateKey rpri = (RSAPrivateKey) privateKey;
		File file = new File("C:\\Users\\wangJin\\AppData\\Local\\abcdefg.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[128];
		fis.read(buffer, 0, 128);
		
		
		System.out.println(decryptByPrivateKey(buffer, rpri));
	}

	public static String decryptByPrivateKey(byte[] bytes, RSAPrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		System.out.println(cipher.getProvider().getName());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		System.out.println("decode = " + bytes.length);
		// 模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		// byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		 System.out.println(bcd.length);
		// 如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for (byte[] arr : arrays) {
			ming += new String(cipher.doFinal(arr));
		}
		return ming;
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

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
}
