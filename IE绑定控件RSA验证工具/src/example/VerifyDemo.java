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
public class VerifyDemo {
	public static void main(String[] args) throws Exception {
		verifyRsa2048();
		// verifyRsa1024();
	}

	
	private static void verifyRsa1024() throws Exception {
		// TODO Auto-generated method stub
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa1024.keystore", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\rsa1024.keystore", "yiersansi", "test1");
		// 公私钥转换
		RSAPublicKey rpub = (RSAPublicKey) publicKey;
		RSAPrivateKey rpri = (RSAPrivateKey) privateKey;
		// 打印公钥
		System.out.println("公钥M是 ： " + rpub.getModulus().toString(16));
		
		
		
		
		System.out.println("公钥E是  ：" + new String(Base64.getEncoder().encode((publicKey.getEncoded()))));
		// 打印私钥
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
		// 私钥解密
		//String enStr = "OAAyAEUANABEADIARQAyAEYAQwA1ADMARgA3ADgAQQBGADkANgBFAEYANgA5ADMAOQAxADgAQgAxADQARQBBADgARAA0ADYAQgA2ADYANABCAEIANgA4ADgAMQA2AEQAQgBFADkANgA2ADUAMwBCAEUANABBADAARgBCAEEAOAAyADEARgA4ADMAQQAyAEMANABDAEYARQA2ADcAOQAwADQANgBDADcANQA0AEIAMwAzAEEAQQA0ADkARAAyAEEAMQA2AEUAOAA0ADAAMgAzADQAMwBDADMAMwA1AEEAOABEADAAMwAyADMANQBFADEANwA2ADgAMQA2ADgANgA5AEYAQwAyADMAMwAzADUANQA1ADgANgA4AEIANwBFAEIAQwBDADMANQA0AEEANQA3AEEANAAzADgAOQA4ADcAQQAxADUAOAAyAEMANgA5AEMANgBEADkAOQA4ADEAOQAyADAAQgA1ADgAOABGADUANQBEADMANQBFAEMARAA1AEYAOQA3AEIAMABEAEEAMQAzAEYARQBEADIARAA4ADcANABGADgAQQBFADIAOQA4ADYANAAyAEQANwBCADIAQwA4ADMAMgBFADgANwA4ADUAMgAxADcAQQAyADUARgBBADQAMgA1ADgAMgA2ADIARABGADYAMQBFAEEARABEAEUANAA=";
		String enStr = "OQA3ADgAMgAzAEEAMwBBAEIARQBEADAAQgAyADIANwBEAEIAQwBEADEARABDADYARABEAEMARgA4ADgARQBFAEUAMAAwAEYAMwAzAEUAQQBFADUAOABEADQANwA2ADkANgAxADUANQBBADIAOQAyADQARQBEAEYAQQA4AEMAQQA1AEEANAA0ADcAQwA2ADcARgBCADgAMgAzAEYANQA4ADIARgA5ADkANwA1AEIAMgAxADYAQgBDADIARQAwADcARAAwADQANwA2AEUARAA3ADYANgA2AEYANgBCADIAQwBDAEQANwA1ADgARAA3ADUAMwBEADgAQwBDAEUAQQA0ADkAQQBEADgAQQBGADIANwA3AEMANwA5AEYANwA5ADUAQwA1AEYANgAzADAARQA4ADcANQAxAEEANgBBADAAOQAyADMAMQAyADEANAAwADEAQgAxADUARQA4ADkAQwAwAEUARAA5ADYANgA1ADQAMABBADYANwAwADQAQQBFAEMAQwAxADgARQBFADgARAAwADQAQgBCADEAMQA2AEEARgBDADcAOQBFAEUARAAyADYAOQA3AEMANABDAEMAMgBBADkAOABDADgAOAA5ADEAQwBEADcAMABDADEAQgA1ADgAOQA2ADYAOABBAEUANwA5AEUAOABEADAANwA1AEMAOAA=";
		String en = base64Filter(enStr, true);
		// 私钥解密
		String decryptByPrivateKey = decryptByPrivateKey(en, rpri);
		System.out.println("decryptByPrivateKey = " + decryptByPrivateKey);
	}

	public static void verifyRsa2048() throws Exception {
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa2048.jks", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\rsa2048.jks", "yiersansi", "test1");
		// 公私钥转换
		RSAPublicKey rpub = (RSAPublicKey) publicKey;
		RSAPrivateKey rpri = (RSAPrivateKey) privateKey;
		// 打印公钥
		System.out.println("公钥M是 ： " + rpub.getModulus().toString(16));
		
		
		
		System.out.println("公钥E是  ：" + new String(Base64.getEncoder().encode((publicKey.getEncoded()))));
		// 打印私钥
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
		// 私钥解密，enStr
		//String enStr = "MgA4AEYAQwA5AEEAOAA2AEQAMwBFADAANwBFADYAOAA0ADQAQQAzAEEANwBCAEQANQAwAEEAQQAwADMAOAA4ADQAQQAxADQAOQAyAEMAMwA3ADUAMgBBADAANABBADkAMgAzAEUANQBDADAAMQA2ADEARABGADcANABGAEIAOQA5ADkAMwA4ADcAOABCADQANQBBADkANwBFADkANwBBAEIAQwBBAEEAMwA0ADQAMwAzAEEAMQAwADkAMgBFAEMARQA2ADIARQAzADMANABEAEYAMgBEAEUAMAA0ADcAOAA4ADUAQQA0AEMARAAxADIAQwAzAEUAMwA5ADkARAA0ADQANAAxADIAMgA3ADMAMQAwADgARgBCADgAQwBGADUAMgBGADMAOAA4ADQANgBCADQAMgA2ADMANAA0ADMANQA2AEQARgA4AEIANwBCAEQAMAA0AEIAMwBBADAAOAA5ADEAMABCADMARABGAEIANgA2AEEAQgAzADIARgA2AEIAMwAxADYAMAA0ADkAQgAyAEEAOAA1ADUAQQA4AEEAQQA1AEQAOAAxAEIAMgA2AEUARABGADYAQQBFAEEARgAxAEIAOQAzADQANAA5ADIAMQBFADgANQBGAEEAOQBBADYANQAzADcAOQA5ADEAMgAzADcAMgAwADYAQwBBAEUAMABFADMARQAwAEEAQQBCADMAQgAyADIAQgA4ADYAOAA1ADMARQBBADEAMwA4ADQARQAyAEYANAA2AEMARgA2ADYARAAwADIARQBBADUAMgBFADgAOQAyAEMARABDADcAMAAyADkAMQAyAEMAMAAyADIAOABDADQAOAA0AEIARgA3ADgANAA3ADUANAA0AEQAQwAyADUAOQBGAEIAQgAzAEUAQQAzADIARQBFADkARQA5ADUAMQBBADEAMAAwAEQANQBGADgAQgA5ADgAMAAzADUAMAA0AEUARQBGAEEARQA5AEYAQQAwAEYAMABCADkAMABEADUANwBDAEEANgA1AEMAQwBBADgAMAA0AEQANQAyADQAMwAyADQARABCAEQARgA3ADMAQQAyADMANQA3ADMANQA2ADkAOQA1ADEAQQBDADIAQwBDADcAMAA4AEUANwA4AEIANQAzAEQANgBCAEEARgA0AEEAMQAyADYAMwAyADQAOAAyAEMAQQBGADAAQwBFAEUAMgBGADcAOQA1AEMARQA2ADEAQQA3ADMAQQAyADcARgBDADUAMgAwAEIANwAxADUARAAxADAANgA0ADQAQwBFADMARgAzAEMARgAwADIAMQBCAEMAQQBGADMAMgBEADIANwA4AEYANwAyAEEAMQAwAEIARQA4ADQAQgBCAA==";
		//String enStr = "MTE1NjEwRkNGOEU4OEVCMDZBNTk3RUZBREI2RUJGMjRGRkMwNjgxOERBMDdFNEREOTVFMEU4NTc2M0FDMTVDNDM4NTFFNEQ0RjRFNEZGRjRDM0JGODY1QTYxRkJDNEFFOUMzRTY2NDIyMTc5RTVBOEEwOEY1Q0RBREI0NDRFNDNDODAxM0I2RDVBMEQ1MzZGQzMwNTU4OTQ0RjI0OTU1NzM4REU0NTc1QUNBNEFBNEMyNEM5MzgwNTQyMDU0MUNDREM0MkY3Njk0NDcxOTRBMEY0MzU5ODVDQjg5Q0YyRDU2N0M0MEMwMUE3ODBFMEQzNzg4Q0UzQkQxMDcxMUE3RTNDOTc1Rjc5RTYzMjMzQjY0MDlDRTE5NEFCQjFEMjUxQUNCMTM4NURDQTY4MUYxRjMxNDczQThGODQwOEI0RkU1NjZEM0MyNjc4RkZFNTc1MTNBOTVFNERFQUEzREUwMkZBRTI3NkI0NzZFRkM3RkI4MDFFRENFRTRBNzM3RkRDQUU4Njg4NjlDNkEzNEI2RkVDQUEzREU5RTBFNzdDOUZENTg5RTc3RUQ0Q0RGMDFFRDU0RjA5MDM3MzM0MUM3REREMTMxNTU5OEMzNzg5NDUwQjg1OTNGMzJDQ0NGNTgyRThBNjhGMkNERjc3MUJCQUY1MTQ1NTRGREM4RTMxMTI";
		//自带Base64解密
		
		//String en = base64Filter(enStr, false);//绑定控件删除乱码
		//String en =new String(Base64.getDecoder().decode(enStr));//非绑定控件不用删除乱码
		String en = "0B14A0E91BEDA16FECBEB175A092BA725F5865E7A13244CFA1CCB654FE5E2D528898BE9E328AD4ECEEC847278F2C307F11B13540D944BB3FE02AD22858F15D4CF5BF22091A4223238D5F7F2AD521EE33B6ED6EAF9BC5D1FD97CA8AC700CE2A20E025F606718ECB7C33C29322058FA256D1D04F268276C7913EDD53940A093418E5029F1B640BE6BDCEA3167178A104CB76619EA089CA5784C5282DD43C7F7950CF13588234B59E7B8F6AC2536626C8EFF1F0264B91EBB48CEAF21B0F0E19ADF49B7270E54A8FBEDA26C1B39958438789C8C301F590C60D4E050B947DE136F8E8DE21353BA6ED64DB71EB0FABA309DEE1DB3770EE6A69750611A3263EEF7CFE8A";
		// 私钥解密
		//String decryptByPrivateKey = decryptByPrivateKey(en, rpri);
		//System.out.println("decryptByPrivateKey = " + decryptByPrivateKey);
	}

	public static String base64Filter(String enStr, boolean isRsa1024){
		byte[] des;
		if(isRsa1024){
			des = new byte[256];
		}else{
			des = new byte[512];	
		}
		byte[] decode = Base64.getDecoder().decode(enStr);
		System.out.println("decode  =  " + new String(decode));
		byte b = decode[1];
		for(int i = 0, j = 0; i < decode.length; i++){
			if(b == decode[i]){
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


	/*public static String cutData(String enStr){
		byte[] des = new byte[512];
		byte[] decode = Base64.getDecoder().decode(enStr);
		System.out.println("decode  =  " + new String(decode));
		byte b = decode[1];
		int times = 0;
		for(int i = 0, j = 0; i < decode.length; i++){
			if(b == decode[i]){
				times++;
				continue;
			}
			des[j++] = decode[i];
		}
		return new String(des);
	}
	
	public static String cutData1024(String enStr){
		byte[] des = new byte[256];
		byte[] decode = Base64.getDecoder().decode(enStr);
		
		byte b = decode[1];
		int times = 0;
		for(int i = 0, j = 0; i < decode.length; i++){
			if(b == decode[i]){
				times++;
				continue;
			}
			des[j++] = decode[i];
		}
		
		return new String(des);
	}
	*/
}
