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
 * ��ȡ֤����Կ
 */
public class CerUtil {

	public static void main(String[] args) throws Exception {
		// �������ַ����Ǵӱ��ػ�ȡ֤����Ϣ�����ӱ��ص�cer�ļ���ȡ֤����Ϣ
		// ��һ��
		// String filePath = "D:\\Temp\\client.cer";
		// System.out.println(getPublicKey(filePath));
		// �ڶ���
		 getX509CerCate("D:\\Temp\\client.cer");
		// ������
		// testReadX509CerFile("D:\\Temp\\client.cer");
		// -------------------------------------------------------
		// ������ʾ�������ȡ֤����Ϣ
		String url = "https://www.baidu.com";
		String url_1 = "https://118.89.58.21:8081/ssl1";
		String url_2 = "https://118.89.58.21:8081/ssl2";
		//getUrlCerInfo(url_1);
	}

	/**
	 * ��ȡ֤�鹫Կ
	 * 
	 * @param filePath ֤��·��
	 * @return
	 */
	public static String getPublicKey(String filePath) {
		String key = "";
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			FileInputStream in = new FileInputStream(filePath);
			// ����һ��֤�����ʹ�ô������� inStream �ж�ȡ�����ݶ������г�ʼ����
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
	 * @cerPath Java��ȡCer֤����Ϣ
	 * @throws Exception
	 * @return X509Cer����
	 */
	public static X509Certificate getX509CerCate(String cerPath) throws Exception {
		X509Certificate x509Certificate = null;
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		FileInputStream fileInputStream = new FileInputStream(cerPath);
		x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
		fileInputStream.close();
		System.out.println("========= ��ȡCer֤����Ϣ =========");
		System.out.println("x509Certificate_SerialNumber_���к�___:   " + x509Certificate.getSerialNumber());
		System.out.println("x509Certificate_getIssuerDN_��������ʶ��___   :" + x509Certificate.getIssuerDN());
		System.out.println("x509Certificate_getSubjectDN_�����ʶ___:   " + x509Certificate.getSubjectDN());
		System.out.println("x509Certificate_getSigAlgOID_֤���㷨OID�ַ���___:   " + x509Certificate.getSigAlgOID());
		System.out.println("x509Certificate_getNotBefore_֤����Ч��___:   " + x509Certificate.getNotAfter());
		System.out.println("x509Certificate_getSigAlgName_ǩ���㷨___:   " + x509Certificate.getSigAlgName());
		System.out.println("x509Certificate_getVersion_�汾��___:   " + x509Certificate.getVersion());
		System.out.println("x509Certificate_getPublicKey_��Կ___:   " + x509Certificate.getPublicKey());
		RSAPublicKey publicKey = (RSAPublicKey) x509Certificate.getPublicKey();
		System.out.println("��Կ    ��" + publicKey.getModulus().toString(16));

		return x509Certificate;
	}

	/***
	 * ��ȡ*.cer��Կ֤���ļ��� ��ȡ��Կ֤����Ϣ
	 * 
	 * @author xgh
	 */
	public static void testReadX509CerFile(String filePath) throws Exception {
		try {
			// ��ȡ֤���ļ�

			File file = new File(filePath);
			InputStream inStream = new FileInputStream(file);
			// ����X509������
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			// CertificateFactory cf = CertificateFactory.getInstance("X509");
			// ����֤�����
			X509Certificate oCert = (X509Certificate) cf.generateCertificate(inStream);
			inStream.close();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			String info = null;
			// ���֤��汾
			info = String.valueOf(oCert.getVersion());
			System.out.println("֤��汾:" + info);
			// ���֤�����к�
			info = oCert.getSerialNumber().toString(16);
			System.out.println("֤�����к�:" + info);
			// ���֤����Ч��
			Date beforedate = oCert.getNotBefore();
			info = dateformat.format(beforedate);
			System.out.println("֤����Ч����:" + info);
			Date afterdate = oCert.getNotAfter();
			info = dateformat.format(afterdate);
			System.out.println("֤��ʧЧ����:" + info);
			// ���֤��������Ϣ
			info = oCert.getSubjectDN().getName();
			System.out.println("֤��ӵ����:" + info);
			// ���֤��䷢����Ϣ
			info = oCert.getIssuerDN().getName();
			System.out.println("֤��䷢��:" + info);
			// ���֤��ǩ���㷨����
			info = oCert.getSigAlgName();
			System.out.println("֤��ǩ���㷨:" + info);

		} catch (Exception e) {
			System.out.println("����֤�����");
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
			// ��һ�����Ƿ���������֤�飬��������֤�����ϵ�����֤��
			X509Certificate x509Certificate = (X509Certificate) certificate;
			System.out.println(x509Certificate.getSubjectDN());
			System.out.println(x509Certificate.getNotBefore());// ��Ч�ڿ�ʼʱ��
			System.out.println(x509Certificate.getNotAfter());// ��Ч�ڽ���ʱ��
		}
		connection.disconnect();

	}

}
