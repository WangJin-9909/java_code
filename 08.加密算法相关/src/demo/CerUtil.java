package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 * 读取证书秘钥
 */
public class CerUtil {

	public static void main(String[] args) throws Exception {
		// 下面三种方法是从本地获取证书信息，即从本地的cer文件获取证书信息
		// 第一种
		// String filePath = "D:\\Temp\\client.cer";
		// System.out.println(getPublicKey(filePath));
		// 第二种
		 getX509CerCate("D:\\Temp\\client.cer");
		// 第三种
		// testReadX509CerFile("D:\\Temp\\client.cer");
		// -------------------------------------------------------
		// 下面演示从网络获取证书信息
		String url = "https://www.baidu.com";
		String url_1 = "https://118.89.58.21:8081/ssl1";
		String url_2 = "https://118.89.58.21:8081/ssl2";
		//getUrlCerInfo(url_1);
	}

	/**
	 * 获取证书公钥
	 * 
	 * @param filePath 证书路径
	 * @return
	 */
	public static String getPublicKey(String filePath) {
		String key = "";
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			FileInputStream in = new FileInputStream(filePath);
			// 生成一个证书对象并使用从输入流 inStream 中读取的数据对它进行初始化。
			Certificate c = cf.generateCertificate(in);
			PublicKey publicKey = c.getPublicKey();
			Provider provider = cf.getProvider();
			key = Base64.getEncoder().encodeToString(publicKey.getEncoded());

		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * @author
	 * @cerPath Java读取Cer证书信息
	 * @throws Exception
	 * @return X509Cer对象
	 */
	public static X509Certificate getX509CerCate(String cerPath) throws Exception {
		X509Certificate x509Certificate = null;
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		FileInputStream fileInputStream = new FileInputStream(cerPath);
		x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
		fileInputStream.close();
		System.out.println("========= 读取Cer证书信息 =========");
		System.out.println("x509Certificate_SerialNumber_序列号___:   " + x509Certificate.getSerialNumber());
		System.out.println("x509Certificate_getIssuerDN_发布方标识名___   :" + x509Certificate.getIssuerDN());
		System.out.println("x509Certificate_getSubjectDN_主体标识___:   " + x509Certificate.getSubjectDN());
		System.out.println("x509Certificate_getSigAlgOID_证书算法OID字符串___:   " + x509Certificate.getSigAlgOID());
		System.out.println("x509Certificate_getNotBefore_证书有效期___:   " + x509Certificate.getNotAfter());
		System.out.println("x509Certificate_getSigAlgName_签名算法___:   " + x509Certificate.getSigAlgName());
		System.out.println("x509Certificate_getVersion_版本号___:   " + x509Certificate.getVersion());
		System.out.println("x509Certificate_getPublicKey_公钥___:   " + x509Certificate.getPublicKey());
		RSAPublicKey publicKey = (RSAPublicKey) x509Certificate.getPublicKey();
		System.out.println("公钥    ：" + publicKey.getModulus().toString(16));

		return x509Certificate;
	}

	/***
	 * 读取*.cer公钥证书文件， 获取公钥证书信息
	 * 
	 * @author xgh
	 */
	public static void testReadX509CerFile(String filePath) throws Exception {
		try {
			// 读取证书文件

			File file = new File(filePath);
			InputStream inStream = new FileInputStream(file);
			// 创建X509工厂类
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			// CertificateFactory cf = CertificateFactory.getInstance("X509");
			// 创建证书对象
			X509Certificate oCert = (X509Certificate) cf.generateCertificate(inStream);
			inStream.close();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			String info = null;
			// 获得证书版本
			info = String.valueOf(oCert.getVersion());
			System.out.println("证书版本:" + info);
			// 获得证书序列号
			info = oCert.getSerialNumber().toString(16);
			System.out.println("证书序列号:" + info);
			// 获得证书有效期
			Date beforedate = oCert.getNotBefore();
			info = dateformat.format(beforedate);
			System.out.println("证书生效日期:" + info);
			Date afterdate = oCert.getNotAfter();
			info = dateformat.format(afterdate);
			System.out.println("证书失效日期:" + info);
			// 获得证书主体信息
			info = oCert.getSubjectDN().getName();
			System.out.println("证书拥有者:" + info);
			// 获得证书颁发者信息
			info = oCert.getIssuerDN().getName();
			System.out.println("证书颁发者:" + info);
			// 获得证书签名算法名称
			info = oCert.getSigAlgName();
			System.out.println("证书签名算法:" + info);

		} catch (Exception e) {
			System.out.println("解析证书出错！");
			e.printStackTrace();
		}
	}

	public static void getUrlCerInfo(String strUrl) throws Exception {
		URL url = new URL(strUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		byte[] buffer = new byte[2048 * 10];
		inputStream.read(buffer);
		System.out.println(new String(buffer));
		for (Certificate certificate : connection.getServerCertificates()) {
			// 第一个就是服务器本身证书，后续的是证书链上的其他证书
			X509Certificate x509Certificate = (X509Certificate) certificate;
			System.out.println(x509Certificate.getSubjectDN());
			System.out.println(x509Certificate.getNotBefore());// 有效期开始时间
			System.out.println(x509Certificate.getNotAfter());// 有效期结束时间
		}
		connection.disconnect();

	}

}
