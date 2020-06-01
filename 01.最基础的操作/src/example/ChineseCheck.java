package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式检查中文
 */
public class ChineseCheck {

    public static void main(String[] args) {
        String str = "Hello! 《满江红》";
        System.out.println("==> " + isContainChinese(str));
    }

    /**
     * 字符串是否包含中文
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}