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
 * 生成、校验、更新
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
	 * if (result == 100) { System.out.println("Token校验成功  "); } if (result == 101)
	 * { System.out.println("Token校验失败   "); } }
	 */

	private void setTimeOut(int time) {
		this.TIME_OUT = time;
	}

	/**
	 * content：有参数(客户端信息)生成令牌 客户端的一些信息，根据这些信息 生成令牌
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

		// 密文：即加密服务端随机数的密文
		String StrTimeStamp = getTimeStamp();
		// 创建随机数A：
		String randomA = RandomStrGenerater.generateString();
		// 创建随机数B：
		String randomB = RandomStrGenerater.generateString();

		// 存储随机数A、B,Demo暂时存储
		// =====================
		RandomItemToken ri = new RandomItemToken();
		ri.setRandomStrA(randomA);
		ri.setRandomStrB(randomB);
		ri.setTimeStamp(StrTimeStamp);

		SysLog.println("" + ri.getRandomStrB() + content.getBytes());
		// 根据客户端信息生成令牌,将content加密，验证时校验
		clientContent = new String(Base64.encode(content.getBytes()), "UTF-8");

		// 将时间戳做3DES加密，密钥为随机数B
		byte[] keyByte = init3DesKey(ri.getRandomStrB());
		byte[] encryptBy3DesCBC = EncodeUtils.encryptBy3DesCBC(keyByte, StrTimeStamp.getBytes());
		SysLog.println("en = " + encryptBy3DesCBC.length);

		String timeStamp = new String(encryptBy3DesCBC, "UTF-8");
		String deb = randomA + timeStamp + clientContent;

		String enStrTimeStamp = new String(Base64.encode(encryptBy3DesCBC), "UTF-8");
		// 返回密文+随机数A作为令牌，content加密
		// return randomA + "" + enStrTimeStamp + "" + clientContent;//
		// 随机数A长度为20，从20开始为时间戳密文

		// 拼接整体，20201013需求，随机数A不做加密
		String str = /* randomA + */ StrTimeStamp + content;
		SysLog.println("generate randomA = " + randomA);
		SysLog.println("generate StrTimeStamp = " + StrTimeStamp);
		SysLog.println("generate content = " + content);
		// 整体做Base64
		String enStr = randomA
				+ new String(Base64.encode(EncodeUtils.encryptBy3DesCBC(keyByte, str.getBytes())), "UTF-8");
		SysLog.println("generate   enStr = " + enStr);
		// SysLog.println("generate strEn = " + encodeUrlEn(enStr));

		// return enStr;
		ri.setToken(enStr);
		return ri;
	}

	/**
	 * content为令牌
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
			randomA = content.substring(0, 19);// 0-20为随机数A
		} catch (Exception e) {
			return ERROR;
			// e.printStackTrace();
		}
		content = content.substring(20);
		String str = "";
		try {
			// 1.解密Base64
			byte[] decode = Base64.decode(content.getBytes());
			// 2.3des解密
			byte[] keyByte = init3DesKey(item.getRandomStrB());
			str = new String(EncodeUtils.decryptBy3DesCBC(keyByte, decode));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		SysLog.println("verify   str = " + str);

		String StrTimeStamp = "";
		String clientInfo = "";
		// todo：获取客户端的内容，这里是否先解密
		try {
			// 从客户端令牌中取出随机数A和时间戳
			// String randomA = str.substring(0, 19);// 0-20为随机数A
			StrTimeStamp = str.substring(0, 13);// 从20位到最后为时间戳密文
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
		} else {// 令牌错误：
			return ERROR;
		}

		// 通过随机数A从 从业务服务器获得randomB，这里暂时使用存储的
		// String randomB = ri.getRandomStrB();

		// =======================================================

		/*
		 * // enStrTimeStamp,解密客户端上送上来的 byte[] enStrTimeStamp =
		 * Base64.decode(b64StrTimeStamp.getBytes()); String clientTimeStamp = new
		 * String(EncodeUtils.decryptBy3DesCBC( keyByte, enStrTimeStamp));
		 * 
		 * SysLog.println("clientTimeStamp = " + clientTimeStamp); // 令牌正确： if
		 * (verifyTimeStamp(clientTimeStamp)) { // 更新时间 String token =
		 * updateToken(clientInfo); return OK + token; } else {// 令牌错误：重新颁发令牌 return
		 * ERROR + generate(""); }
		 */

	}

	/**
	 * 
	 * @param token 当前Token，根据当前Token更新新的Token
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static RandomItemToken updateToken(RandomItemToken rToken) throws UnsupportedEncodingException {

		if (null == rToken || "".equals(rToken.getToken())) {
			throw new IllegalArgumentException("current token is null");
		}

		SysLog.println("updateToken = " + rToken.getToken());

		// 密文：即加密服务端随机数的密文
		String StrTimeStamp = getTimeStamp();
		// 创建随机数A：
		String randomA = RandomStrGenerater.generateString();
		// 创建随机数B：
		String randomB = RandomStrGenerater.generateString();

		// 存储随机数A、B,Demo暂时存储
		// =====================
		RandomItemToken ri = new RandomItemToken();
		ri.setRandomStrA(randomA);
		ri.setRandomStrB(randomB);
		ri.setTimeStamp(StrTimeStamp);

		SysLog.println("" + ri.getRandomStrB() + rToken.getToken().getBytes());

		// 将时间戳做3DES加密，密钥为随机数B
		byte[] keyByte = init3DesKey(ri.getRandomStrB());
		// 解密传递进来的当前token

		String clientInfo = "";
		try {
			// 获取当前随机数A
			String randomACur = rToken.getToken().substring(0, 19);
			SysLog.println("randomACur = " + randomACur);
			String enStrNorandomA = rToken.getToken().substring(20);
			SysLog.println("enStrNorandomA = " + enStrNorandomA);
			// 1.解密Base64
			// byte[] decode = Base64.decode(rToken.getToken().substring(20).getBytes());
			byte[] decode = Base64.decode(enStrNorandomA);
			// 2.3des解密
			byte[] keyByteCur = init3DesKey(rToken.getRandomStrB());
			String str = new String(EncodeUtils.decryptBy3DesCBC(keyByteCur, decode), "UTF-8");
			// 3.找出客户端信息
			clientInfo = str.substring(13);
			SysLog.println("上一次UA信息 = " + clientInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// 根据当前传递进来的Token，解析出来的客户端信息不能为空
		if ("".equals(clientInfo)) {

			return null;
		}

		// 返回密文+随机数A作为令牌，content加密
		// return randomA + "" + enStrTimeStamp + "" + clientContent;//
		// 随机数A长度为20，从20开始为时间戳密文

		// 拼接整体,20201013,随机数A不做加密
		String str = /* randomA + */ StrTimeStamp + clientInfo;
		SysLog.println("generate randomA = " + randomA);
		SysLog.println("generate StrTimeStamp = " + StrTimeStamp);
		SysLog.println("generate content = " + clientInfo);
		// 整体做Base64
		String enStr = randomA  + new String(Base64.encode(EncodeUtils.encryptBy3DesCBC(keyByte, str.getBytes())));
		SysLog.println("generate   str = " + str);
		ri.setToken(enStr);
		return ri;

		// 第一个版本
		/*
		 * // TODO Auto-generated method stub String strTimeStamp = getTimeStamp(); //
		 * 创建随机数A： String randomA = RandomStrGenerater.generateString(); // 创建随机数B：
		 * String randomB = RandomStrGenerater.generateString();
		 * ri.setRandomStrA(randomA); ri.setRandomStrB(randomB);
		 * 
		 * // 将新时间戳做3DES加密，密钥为随机数B byte[] keyByte = init3DesKey(ri.getRandomStrB());
		 * byte[] des3StrTimeStamp = EncodeUtils.encryptBy3DesCBC(keyByte,
		 * strTimeStamp.getBytes()); String enStrTimeStamp = new
		 * String(Base64.encode(des3StrTimeStamp), "UTF-8"); // 返回密文+随机数A作为令牌，content加密
		 * String str = ri.getRandomStrA() + enStrTimeStamp + clientInfo clientContent ;
		 * 
		 * return str;
		 */

	}

	/**
	 * 时间戳校验
	 * 
	 * @param ClientTimeStamp
	 * @return
	 * @throws IllegalAccessException
	 */
	private static boolean verifyTimeStamp(String ClientTimeStamp) throws IllegalArgumentException {
		if (null == ClientTimeStamp || "".equals(ClientTimeStamp)) {
			throw new IllegalArgumentException("timeStamp Exception");
		}
		// 现在的时间戳
		long serverTimeStamp = System.currentTimeMillis();
		// 客户端传过来的时间戳
		long clientTimeStamp = Long.parseLong(ClientTimeStamp);

		return serverTimeStamp - clientTimeStamp <= TIME_OUT;// 上一次颁发令牌时间在此次请求的一定范围时间内就行
	}

	/**
	 * 服务器时间戳
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
