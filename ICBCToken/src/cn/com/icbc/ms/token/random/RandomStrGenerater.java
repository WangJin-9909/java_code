package cn.com.icbc.ms.token.random;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStrGenerater {	 
	 
	
	 public static String generateString() {
		  
		  Random random = new SecureRandom();
		  byte[] nbyte = new byte[10];
      	  random.nextBytes(nbyte);
      	  String str = bytesToHexString(nbyte);
      	  return str;
	 }
	 
	 public static String bytesToHexString(byte[] src){  
		    StringBuilder stringBuilder = new StringBuilder("");  
		    if (null == src || src.length <= 0) {  
		        return null;  
		    }  
		    for (int i = 0; i < src.length; i++) {  
		        int v = src[i] & 0xFF;  
		        String hv = Integer.toHexString(v);  
		        if (hv.length() < 2) {  
		            stringBuilder.append(0);  
		       }  
		        stringBuilder.append(hv);  
		    }  
		    return stringBuilder.toString();  
	 }

}
