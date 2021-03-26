package demo;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class Demo {
	public static void main(String[] args) {
		String hexStr = "8014f3d4a6a1b884ce972bc53c9866633e8abad53be846e0af35ba0e78b5a19cc563b206aa6ff7836cf6f44abaecd5d361b42f4d155e12c763b1467ea99ac028aeeafc948422f1b15993d013602f759f517a4d8d80946fa160d7038f470c5973c31d52b40b74e48b455db6a93b3182f70aa79a5038a0038db32456f3d3a9e4dbcfc93421fe516cb4ac1eb10e39b8482b481234ab5e6dd510a7deb45d75fa92a9430ebf736f5d6a229c6032b22e6560e7b88563c9bcd15c973c071ecebd1e3db63acbc9051bbcc9a781ba062c834d161ef7861fd863693b6708c658b5a297ef5ccccf853fb9b84c70dfb5ad0a3f724156fa1ed99c06f8fd92223d8b731241faaf";
		byte[] hexToByte = new Demo().hexToByte(hexStr);
		for (int i = 0; i < hexToByte.length; i++) {
			System.out.print(hexToByte[i]);
		}
	}

	public byte[] hexToByte(String hex) {
		hex = hex.replace(" ", "");
		int byteLength = hex.length() / 2;

		byte[] bytes = new byte[byteLength];

		int m = 0;
		int n = 0;

		for (int i = 0; i < byteLength; i++) {

			m = i * 2 + 1;

			n = m + 1;

			int intHex = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
			bytes[i] = Byte.valueOf((byte) intHex);

		}

		return bytes;

	}

	public static String byteToHex(byte[] bytes) {
		String strHex = "";
		StringBuilder stringBuilder = new StringBuilder();
		for (int n = 0; n < bytes.length; n++) {
			strHex = Integer.toHexString(bytes[n] & 0xFF);
			stringBuilder.append((strHex.length() == 1) ? "0" + strHex : strHex);
		}
		return stringBuilder.toString().trim();
	}

	/**
	 * 
	 * 字符串转为16进制字符串
	 * 
	 */

	public String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}
}
