package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������ʽ�������
 */
public class ChineseCheck {

    public static void main(String[] args) {
        String str = "Hello! �������졷";
        System.out.println("==> " + isContainChinese(str));
    }

    /**
     * �ַ����Ƿ��������
     * @param str ��У���ַ���
     * @return true ���������ַ� false �����������ַ�
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��|\\��]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}