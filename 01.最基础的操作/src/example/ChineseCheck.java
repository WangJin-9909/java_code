package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������ʽ�������
 */
public class ChineseCheck {

    public static void main(String[] args) {
        String str = "1591694372072|{\"realname��flag\":\"1\",\"custphonenum\":\"\",\"defaultphonenum\":\"\",\"cisno\":\"\",\"userid\":\"\",\"deviceId\":\"357404071198314-35E2442CB7BF7E80\"}";
        System.out.println("str = " + str.length());
    	System.out.println("==> " + isContainChinese(str));
    	  //���ַ�ƥ�����
    	//System.out.println("==> " + isConChinese(str));
    }
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[^\\x00-\\xff]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    
    public static boolean isConChinese(String str) {
    	Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��]");
        for(int i  = 0; i < str.length(); i++) {
        	Matcher m = p.matcher(str.charAt(i)+ "");
        	if (m.find()) {
                 if(str.charAt(i) == '|' ) {
                	continue;
                }
                return true;
            }
        }
        return false;
    }
}