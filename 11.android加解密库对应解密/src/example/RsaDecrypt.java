package example;

import java.math.BigInteger;
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

import com.icbc.ms.universal.component.CommonDecryptUtils;

/**
 * 验证前请将test.jks复制到D盘
 * 
 * @author wangJin
 *
 */
public class RsaDecrypt {
	public static void main(String[] args) throws Exception {
		CommonDecryptUtils utils = new CommonDecryptUtils();
		//long timeStart_r = System.nanoTime();
		decrypt();
		//long timeEnd_r = System.nanoTime();
		

		// 计算签名
		// RSA签名
		//String modulus = "16168835080510279212317728548997977232948701220029325643282129233849120020232675469169764623424507340138475677736006697643099938376601070043845617298501653866423274471642132087587326468778937973983751624816744931634252997762304982752073055594379301570053969260228697442786249619887739536844449973819028020936762258968046179576395079926402808672526281213753401550156169102921378560246738375519195117752783882014857346813053263753800415450821417400084930103838533170451775073123134259960760754562974462238889945230210560247093517607703650302441567279177005051340483702944899331831161832830060403062062402739144721824431";
		//String exponents = "12213284279499837681410147460054562536275577234178124367075675200153605405825691844695058179623827934849246989496670210112211717799033513457291811324061612112294090047517770219325312564054389817417514387825627372257229662652015592202246433986056013319559206162295519145027854512457431039442569106061265592694827131861594066518114326617964041510907063035057754386214563952983389001190285564075215830245174693783417663246590534137750278039616739918758649414499228694912188293045289194907514963646540056654017538427372254018348504484194615727124976196217814080620572159638412257104312882683080089818906990059161194205697";
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa2048.jks", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\rsa2048.jks", "yiersansi", "test1");
		// 公私钥转换
		RSAPublicKey rpub = (RSAPublicKey) publicKey;
		RSAPrivateKey rpri = (RSAPrivateKey) privateKey;
		String modulus = rpri.getModulus().toString();
		String modulesPub = rpub.getModulus().toString(16);
		String exponents = new BigInteger( rpri.getEncoded()).toString(10);
		
		long timeStart_r = System.nanoTime();
		//	decrypt();
		
		String singleEtPcInfoR = utils.getArsQian("abz", modulus, exponents);
		
		
		//BigInteger b = new BigInteger(modulus, 10);
		//String c = b.toString(16);
		//System.out.println("c = " + c);
		
		
		//long timeEnd_r = System.nanoTime();
		System.out.println("modulesPub = " + modulesPub);
		System.out.println("modules = " + modulus);
		System.out.println("exponents = " + exponents);
		//System.out.println("耗时： " + (timeEnd_r - timeStart_r));
		System.out.println("签名的结果" + singleEtPcInfoR);

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

		// 打印公钥，将公钥M复制到android对应的工程
		System.out.println("公钥M是 ： " + rpub.getModulus().toString(16));
		System.out.println("公钥E是  ：" + new String(Base64.getEncoder().encode((publicKey.getEncoded()))));
		// 打印私钥
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
		// ------------------------------------------------------------------------------------------------------------------------------------------
		// 将android生成的Log密文，复制到下面
		String str = "AC74FC3B223F078C455DEE12BAB6C6B1B3D137CE8A4AE8023442240A22CED770CA6A9034142AB74D9DA2DB9851E08BCE7D9B69EBE67AAC4FB325E9F47BBCB4E80DF11907C969F78DCF6D02AAED3162D0CDD23D105B2B00E50417C4A489940AB3875E249B02966B95AF41467263F4F292CFB64BF75AFB3254893B2E4502BA2D9BF88DD56796E58678B15834259798DDF124748D8CA8216008F8C2B5496C5BA63923E2E0E2255DF9DFBBCF043CF280741CF03257F30960EC6C32D298F4CB2A2FB19D0989B5C26DD5A11C368E82ED4EECCE65C057B288D00F83EB7CEE4D936F9A30C26C01E468EADA47181FB3D50788D0A6A0B7AD7FE5572A834BF9E728F99D1264";
//		String str = "9A2ED98A3DF75FFF37C534263B7E4E69673B22ADFA201B38553CEE873D404CA218273A382A1220C1B017F653CED7BB36411F03F58352D29E46725A4F1442A45D20044705D64E113A41E41A8AB57DA218FE9E64ADEB8CDBE22DC9CD366901B40325FD0D09F1A1DAEA5A8FF42DD6720A51505931673253FB16B6759F2EF05FB4689E0E331A306326CA2F9BE759E15918A116A92E8D54E11A365338CB3C0A0056B7225DA37A96AC63C2A0E7492A67EFCD8C1006D0E5FDEBA286FCAF0AEF8EF35816A76E26DD3136CC0C960D105C8A5A8D946DC012B55EC66A1FEF75F7294238C0CE29965BE47EACE1DC3F9881F1C25B9924D0BE3A83611F7B2428B2CB20B6BB8003";
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
			ming += new String(cipher.doFinal(arr), "UTF-8");
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
