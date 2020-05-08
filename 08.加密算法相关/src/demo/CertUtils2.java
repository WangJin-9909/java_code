package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Title: Restful.java
 * @Package Restful
 * @Description: TODO(��һ�仰�������ļ���ʲô)
 * @author Ishadow
 * @date 2016��6��19�� ����1:15:43
 * @version V1.0
 */
public class CertUtils2 {
	static String url_1 = "https://118.89.58.21:8081/ssl1";
	static String url_2 = "https://118.89.58.21:8082/ssl2";
	static String url_3 = "https://mybank.icbc.com.cn/icbc/newperbank/perbank3/frame/frame_index.jsp";
	static String url_4 = "https://imgsa.baidu.com/normandy/pic/item/3801213fb80e7bec99dc95b8202eb9389b506b61.jpg";
	static String url_5 = "https://m.baidu.com/bdlogo/wiseshouy1_5c1f0126fd27d6f7b7c0d6aae5943555.png";
	static String url_6 = "https://stackoverflow.com/questions/2642777/trusting-all-certificates-using-httpclient-over-https/6378872#6378872";
	static String url_7 = "https://www.12306.cn/index";
	static String url_8 = "https://juejin.im";
	static String url_9 = "sp0.baidu.com";
	static String url_10 = "https://www.baidu.com";
    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new CertUtils2().new NullHostNameVerifier());
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        
        
        URL url = new URL(url_3);
        // ��restful����
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// POST GET PUT DELETE
        // ���÷����ύģʽ�����ύ
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setConnectTimeout(130000);// ���ӳ�ʱ ��λ����
        
        conn.setReadTimeout(130000);// ��ȡ��ʱ ��λ����
        // ��ȡ���󷵻�ֵ
        byte bytes[] = new byte[1024];
        InputStream inStream = conn.getInputStream();
        //inStream.read(bytes, 0, 1024);
        //System.out.println(new String(bytes, "utf-8"));
        System.out.println("conn = " + conn.getServerCertificates().length);
        for (Certificate certificate : conn.getServerCertificates()) {
        	
        	System.out.println("=============================================================================================");
			// ��һ�����Ƿ���������֤�飬��������֤�����ϵ�����֤��
			X509Certificate x509Certificate = (X509Certificate) certificate;
			System.out.println(x509Certificate.getSubjectDN());
			System.out.println(x509Certificate.getNotBefore());// ��Ч�ڿ�ʼʱ��
			System.out.println(x509Certificate.getNotAfter());// ��Ч�ڽ���ʱ��
			System.out.println("x509Certificate_SerialNumber_���к�___:   " + x509Certificate.getSerialNumber());
			System.out.println("x509Certificate_getIssuerDN_��������ʶ��___   :" + x509Certificate.getIssuerDN());
			System.out.println("x509Certificate_getSubjectDN_�����ʶ___:   " + x509Certificate.getSubjectDN());
			System.out.println("x509Certificate_getSigAlgOID_֤���㷨OID�ַ���___:   " + x509Certificate.getSigAlgOID());
			System.out.println("x509Certificate_getNotBefore_֤����Ч��___:   " + x509Certificate.getNotAfter());
			System.out.println("x509Certificate_getSigAlgName_ǩ���㷨___:   " + x509Certificate.getSigAlgName());
			System.out.println("x509Certificate_getVersion_�汾��___:   " + x509Certificate.getVersion());
			System.out.println("x509Certificate_getPublicKey_��Կ___:   " + x509Certificate.getPublicKey());
			System.out.println("issueDN = " + x509Certificate.getIssuerDN());
			
		}
        conn.disconnect();
        
        
        
        
    }

    static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    } };

    public class NullHostNameVerifier implements HostnameVerifier {
        /*
         * (non-Javadoc)
         * 
         * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
         * javax.net.ssl.SSLSession)
         */
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }
}