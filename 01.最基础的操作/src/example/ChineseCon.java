package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseCon {

	/**
	 * ������������Unicode����ת��
	 * @param args
	 */
	public static void main(String[] args) {
        String sourceData = "01234һ�������";
        String unicodeEncode = unicodeEncode(sourceData);
        System.out.println("����󳤶ȣ�" + unicodeEncode.length());
        System.out.println("��������" + unicodeEncode.length());
        System.out.println(unicodeEncode);//\u8fd9\u662f\u539f\u59cb\u7684\u6570\u636e\uff01\uff01\uff01

        String unicodeDecode = unicodeDecode("\\u4e00\\u4e8c\\u4e09\\u56db\\u4e94\\u516d\\u4e03\\u516b\\u4e5d\\u5341\\u4e00\\u4e8c\\u4e09\\u56db\\u4e94\\u516d\\u4e03\\u516b\\u4e5d\\u5341\\u4e00\\u4e8c\\u4e09\\u56db\\u4e94\\u516d\\u4e03\\u516b\\u4e5d\\u5341\\u4e00\\u4e8c\\u4e09\\u56db\\u4e94\\u516d\\u4e03\\u516b\\u4e5d\\u5341");
       // String unicodeDecode = unicodeDecode(unicodeEncode);
        System.out.println("��������");
        System.out.println(unicodeDecode);//����ԭʼ�����ݣ�����
    }
	
	
	/**
     * @Title: unicodeDecode 
     * @Description: unicode����
     * @param str
     * @return
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }
    
    
    /**
     * @Title: unicodeEncode 
     * @Description: unicode����
     * @param string
     * @return
     */
    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
    
    
    
    
}
