package demo;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.security.PrivateKey;
import java.security.PublicKey;

import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Test {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {

		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\test.keystore", "yiersansi", "test1", "yiersansi");
		PublicKey publicKey = JKSUtil.getPublicKey("D:\\test.keystore", "yiersansi", "test1");
		// ¥Ú”°π´‘ø
		System.out.println(new String(Base64.getEncoder().encode((publicKey.getEncoded()))));
		

		// byte[] encryptByPublicKey = encryptByPublicKey("wangjin", publicKey);
		// byte[] encode = Base64.getEncoder().encode(encryptByPublicKey);
		// System.out.println(new String(encode));

		// System.out.println(encryptBase64(privateKey.getEncoded()));

		// ÀΩ‘øΩ‚√‹
		// ¥Ú”°ÀΩ‘ø
		System.out.println(new String(Base64.getEncoder().encode((privateKey.getEncoded()))));
		String enStr = "5DBA04F1DF9A750B7237CBA735C452344D12F49598610F11E8A713564BCA0F85FDD8FE394E25F7CA2E5CD397B6DAA5F532972CB714720834B770429FA78A62CB1185366F1F903EDA4F8DEA454477C5C20A2AAACCCF79868FF318EA3704D5B6E46AA97C4629075F0BEA949BBF2C41B21E422CB283ECD2512469BB7A89DD75AA7206EE2ABD64058FA72967A617038492FD218A247860A3B1A6410680276C5BEA0C99760184C61FB2BA148CAC693C5DC4BFF424D4820844DA8CAABFFC75244EF478670177F41A145DCE16540FFFAAA0F071D1C914209B42E6C8D2D64CF17E6DCF9B975B4A945C31F4F8E43E1D552DB50EF217860A4EF4772A5FF49CAC1CFCA860B1";
		
		String decryptByPrivate = decryptByPrivate(privateKey, enStr);
		System.out.println(decryptByPrivate);

	}

	public static byte[] encryptByPublicKey(String str, PublicKey publicKey) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(str.getBytes());
	}

	public static String decryptByPrivate(PrivateKey privateKey, String enStr) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decode = Base64.getDecoder().decode(enStr);

		return new String(cipher.doFinal(decode));
	}

}
