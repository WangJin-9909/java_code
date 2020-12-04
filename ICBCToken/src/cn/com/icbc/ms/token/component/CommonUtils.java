package cn.com.icbc.ms.token.component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import cn.com.icbc.ms.token.random.RandomItemToken;
import cn.com.icbc.ms.token.random.RandomStrGenerater;
import cn.com.icbc.ms.token.utils.Base64;
import cn.com.icbc.ms.token.utils.EncodeUtils;
import cn.com.icbc.ms.token.utils.HexStringUtil;
import cn.com.icbc.ms.token.utils.StringUtils;
import cn.com.icbc.ms.token.utils.SysLog;

/**
 * ���ɡ�У�顢����
 * 
 * @author wangJin
 * 
 */
public class CommonUtils {
	private static int TIME_OUT = 1000 * 60 * 5;// 5 minute
	public static final int ERROR = 101;
	public static final int OK = 100;
	public static final int CURRENT_TOKEN_ERROR = 102;

	/*
	 * public static void main(String[] args) throws UnsupportedEncodingException {
	 * 
	 * RandomItemToken token = CommonUtils.generate("123455"); String tokenStr =
	 * token.getToken(); System.out.println("generate = " + token.getToken());
	 * 
	 * // token.setRandomStrB("ffdsfdsfsdf"); int result =
	 * CommonUtils.verify(tokenStr, token); RandomItemToken updateToken =
	 * CommonUtils.updateToken(token); System.out.println("updateToken = " +
	 * updateToken.getToken());
	 * 
	 * if (result == 100) { System.out.println("TokenУ��ɹ�  "); } if (result == 101)
	 * { System.out.println("TokenУ��ʧ��   "); } }
	 */

	private void setTimeOut(int time) {
		this.TIME_OUT = time;
	}

	/**
	 * content���в���(�ͻ�����Ϣ)�������� �ͻ��˵�һЩ��Ϣ��������Щ��Ϣ ��������
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 * 
	 */
	public static RandomItemToken generate(String content) throws UnsupportedEncodingException {
		String clientContent = "";
		if ("".equals(content) || null == content) {
			throw new IllegalArgumentException("content is null && clientContent is null");
		}

		// ���ģ������ܷ���������������
		String StrTimeStamp = getTimeStamp();
		// ���������A��
		String randomA = RandomStrGenerater.generateString();
		// ���������B��
		String randomB = RandomStrGenerater.generateString();

		// �洢�����A��B,Demo��ʱ�洢
		// =====================
		RandomItemToken ri = new RandomItemToken();
		ri.setRandomStrA(randomA);
		ri.setRandomStrB(randomB);
		ri.setTimeStamp(StrTimeStamp);

		SysLog.println("" + ri.getRandomStrB() + content.getBytes());
		// ���ݿͻ�����Ϣ��������,��content���ܣ���֤ʱУ��
		clientContent = new String(Base64.encode(content.getBytes()), "UTF-8");

		// ��ʱ�����3DES���ܣ���ԿΪ�����B
		byte[] keyByte = init3DesKey(ri.getRandomStrB());
		byte[] encryptBy3DesCBC = EncodeUtils.encryptBy3DesCBC(keyByte, StrTimeStamp.getBytes());
		SysLog.println("en = " + encryptBy3DesCBC.length);

		String timeStamp = new String(encryptBy3DesCBC, "UTF-8");
		String deb = randomA + timeStamp + clientContent;

		String enStrTimeStamp = new String(Base64.encode(encryptBy3DesCBC), "UTF-8");
		// ��������+�����A��Ϊ���ƣ�content����
		// return randomA + "" + enStrTimeStamp + "" + clientContent;//
		// �����A����Ϊ20����20��ʼΪʱ�������

		// ƴ�����壬20201013���������A��������
		String str = /* randomA + */ StrTimeStamp + content;
		SysLog.println("generate randomA = " + randomA);
		SysLog.println("generate StrTimeStamp = " + StrTimeStamp);
		SysLog.println("generate content = " + content);
		// ������Base64
		String enStr = randomA
				+ new String(Base64.encode(EncodeUtils.encryptBy3DesCBC(keyByte, str.getBytes())), "UTF-8");
		SysLog.println("generate   enStr = " + enStr);
		// SysLog.println("generate strEn = " + encodeUrlEn(enStr));

		// return enStr;
		ri.setToken(enStr);
		return ri;
	}

	/**
	 * contentΪ����
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 * @throws IllegalAccessException
	 */
	public static int verify(String content, RandomItemToken item)
			throws IllegalArgumentException, UnsupportedEncodingException {
		if (null == content || "".equals(content) || null == item || "".equals(item.getToken())) {
			return ERROR;
		}
		String randomA = "";
		try {
			randomA = content.substring(0, 19);// 0-20Ϊ�����A
		} catch (Exception e) {
			return ERROR;
			// e.printStackTrace();
		}
		content = content.substring(20);
		String str = "";
		try {
			// 1.����Base64
			byte[] decode = Base64.decode(content.getBytes());
			// 2.3des����
			byte[] keyByte = init3DesKey(item.getRandomStrB());
			str = new String(EncodeUtils.decryptBy3DesCBC(keyByte, decode));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		SysLog.println("verify   str = " + str);

		String StrTimeStamp = "";
		String clientInfo = "";
		// todo����ȡ�ͻ��˵����ݣ������Ƿ��Ƚ���
		try {
			// �ӿͻ���������ȡ�������A��ʱ���
			// String randomA = str.substring(0, 19);// 0-20Ϊ�����A
			StrTimeStamp = str.substring(0, 13);// ��20λ�����Ϊʱ�������
			clientInfo = str.substring(13);
			SysLog.println("verify randomA = " + randomA);
			SysLog.println("verify StrTimeStamp = " + StrTimeStamp);
			SysLog.println("verify content = " + clientInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		if (verifyTimeStamp(StrTimeStamp)) {
			return OK;
		} else {// ���ƴ���
			return ERROR;
		}

		// ͨ�������A�� ��ҵ����������randomB��������ʱʹ�ô洢��
		// String randomB = ri.getRandomStrB();

		// =======================================================

		/*
		 * // enStrTimeStamp,���ܿͻ������������� byte[] enStrTimeStamp =
		 * Base64.decode(b64StrTimeStamp.getBytes()); String clientTimeStamp = new
		 * String(EncodeUtils.decryptBy3DesCBC( keyByte, enStrTimeStamp));
		 * 
		 * SysLog.println("clientTimeStamp = " + clientTimeStamp); // ������ȷ�� if
		 * (verifyTimeStamp(clientTimeStamp)) { // ����ʱ�� String token =
		 * updateToken(clientInfo); return OK + token; } else {// ���ƴ������°䷢���� return
		 * ERROR + generate(""); }
		 */

	}

	/**
	 * 
	 * @param token ��ǰToken�����ݵ�ǰToken�����µ�Token
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static RandomItemToken updateToken(RandomItemToken rToken) throws UnsupportedEncodingException {

		if (null == rToken || "".equals(rToken.getToken())) {
			throw new IllegalArgumentException("current token is null");
		}

		SysLog.println("updateToken = " + rToken.getToken());

		// ���ģ������ܷ���������������
		String StrTimeStamp = getTimeStamp();
		// ���������A��
		String randomA = RandomStrGenerater.generateString();
		// ���������B��
		String randomB = RandomStrGenerater.generateString();

		// �洢�����A��B,Demo��ʱ�洢
		// =====================
		RandomItemToken ri = new RandomItemToken();
		ri.setRandomStrA(randomA);
		ri.setRandomStrB(randomB);
		ri.setTimeStamp(StrTimeStamp);

		SysLog.println("" + ri.getRandomStrB() + rToken.getToken().getBytes());

		// ��ʱ�����3DES���ܣ���ԿΪ�����B
		byte[] keyByte = init3DesKey(ri.getRandomStrB());
		// ���ܴ��ݽ����ĵ�ǰtoken

		String clientInfo = "";
		try {
			// ��ȡ��ǰ�����A
			String randomACur = rToken.getToken().substring(0, 19);
			SysLog.println("randomACur = " + randomACur);
			String enStrNorandomA = rToken.getToken().substring(20);
			SysLog.println("enStrNorandomA = " + enStrNorandomA);
			// 1.����Base64
			// byte[] decode = Base64.decode(rToken.getToken().substring(20).getBytes());
			byte[] decode = Base64.decode(enStrNorandomA);
			// 2.3des����
			byte[] keyByteCur = init3DesKey(rToken.getRandomStrB());
			String str = new String(EncodeUtils.decryptBy3DesCBC(keyByteCur, decode), "UTF-8");
			// 3.�ҳ��ͻ�����Ϣ
			clientInfo = str.substring(13);
			SysLog.println("��һ��UA��Ϣ = " + clientInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// ���ݵ�ǰ���ݽ�����Token�����������Ŀͻ�����Ϣ����Ϊ��
		if ("".equals(clientInfo)) {

			return null;
		}

		// ��������+�����A��Ϊ���ƣ�content����
		// return randomA + "" + enStrTimeStamp + "" + clientContent;//
		// �����A����Ϊ20����20��ʼΪʱ�������

		// ƴ������,20201013,�����A��������
		String str = /* randomA + */ StrTimeStamp + clientInfo;
		SysLog.println("generate randomA = " + randomA);
		SysLog.println("generate StrTimeStamp = " + StrTimeStamp);
		SysLog.println("generate content = " + clientInfo);
		// ������Base64
		String enStr = randomA  + new String(Base64.encode(EncodeUtils.encryptBy3DesCBC(keyByte, str.getBytes())));
		SysLog.println("generate   str = " + str);
		ri.setToken(enStr);
		return ri;

		// ��һ���汾
		/*
		 * // TODO Auto-generated method stub String strTimeStamp = getTimeStamp(); //
		 * ���������A�� String randomA = RandomStrGenerater.generateString(); // ���������B��
		 * String randomB = RandomStrGenerater.generateString();
		 * ri.setRandomStrA(randomA); ri.setRandomStrB(randomB);
		 * 
		 * // ����ʱ�����3DES���ܣ���ԿΪ�����B byte[] keyByte = init3DesKey(ri.getRandomStrB());
		 * byte[] des3StrTimeStamp = EncodeUtils.encryptBy3DesCBC(keyByte,
		 * strTimeStamp.getBytes()); String enStrTimeStamp = new
		 * String(Base64.encode(des3StrTimeStamp), "UTF-8"); // ��������+�����A��Ϊ���ƣ�content����
		 * String str = ri.getRandomStrA() + enStrTimeStamp + clientInfo clientContent ;
		 * 
		 * return str;
		 */

	}

	/**
	 * ʱ���У��
	 * 
	 * @param ClientTimeStamp
	 * @return
	 * @throws IllegalAccessException
	 */
	private static boolean verifyTimeStamp(String ClientTimeStamp) throws IllegalArgumentException {
		if (null == ClientTimeStamp || "".equals(ClientTimeStamp)) {
			throw new IllegalArgumentException("timeStamp Exception");
		}
		// ���ڵ�ʱ���
		long serverTimeStamp = System.currentTimeMillis();
		// �ͻ��˴�������ʱ���
		long clientTimeStamp = Long.parseLong(ClientTimeStamp);

		return serverTimeStamp - clientTimeStamp <= TIME_OUT;// ��һ�ΰ䷢����ʱ���ڴ˴������һ����Χʱ���ھ���
	}

	/**
	 * ������ʱ���
	 */
	private static String getTimeStamp() {
		String timeStamp = System.currentTimeMillis() + "";

		return timeStamp;
	}

	private static byte[] init3DesKey(String str) {
		if(null == str) {
			throw new IllegalArgumentException("str is null");
		}
		byte[] key = null;
		if (StringUtils.isEmpty(str)) {
			return key;
		}
		byte[] iKey = EncodeUtils.encryptBySha(str);
		if (iKey == null) {
			return null;
		}
		String hexStr = HexStringUtil.byte2HexNew(iKey);
		String keyStr = "";
		if (hexStr.length() > 28) {
			keyStr = hexStr.substring(4, 28);
		} else {
			for (int i = 0; i < 24; i++) {
				if (hexStr.length() >= (i + 4)) {
					char c = hexStr.charAt(i + 4);
					keyStr.concat(String.valueOf(c));
				} else {
					keyStr.concat("0");
				}
			}
		}
		/*
		 * for (int i = 0; i < key.length; i++) { key[i] = iKey[i + 4]; }
		 */
		try {
			key = keyStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return key;
	}

	public static String decodeUrlEn(String content) throws UnsupportedEncodingException {
		if(null == content) {
			throw new IllegalArgumentException("content is null");
		}
		return URLDecoder.decode(content, "UTF-8");
	}

	public static String encodeUrlEn(String content) throws UnsupportedEncodingException {
		if(null == content) {
			throw new IllegalArgumentException("content is null");
		}
		return URLEncoder.encode(content, "UTF-8");
	}

}
