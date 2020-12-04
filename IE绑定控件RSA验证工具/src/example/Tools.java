package example;
import java.util.Arrays;

public class Tools {
	/**
	 * 将一长串字符，分割成0xXX,注意：需要去除最后一个，逗号
	 * @param args
	 */
    public static void main(String[] args) {
        String str = "8014f3d4a6a1b884ce972bc53c9866633e8abad53be846e0af35ba0e78b5a19cc563b206aa6ff7836cf6f44abaecd5d361b42f4d155e12c763b1467ea99ac028aeeafc948422f1b15993d013602f759f517a4d8d80946fa160d7038f470c5973c31d52b40b74e48b455db6a93b3182f70aa79a5038a0038db32456f3d3a9e4dbcfc93421fe516cb4ac1eb10e39b8482b481234ab5e6dd510a7deb45d75fa92a9430ebf736f5d6a229c6032b22e6560e7b88563c9bcd15c973c071ecebd1e3db63acbc9051bbcc9a781ba062c834d161ef7861fd863693b6708c658b5a297ef5ccccf853fb9b84c70dfb5ad0a3f724156fa1ed99c06f8fd92223d8b731241faaf";
//        String str = "b3892246eb67960b72993110fce7723ecba365611ffaa609e22f31c39ca48259d6ddfbbe8b353f8c8c8d17c70a19c8a9976e6769ace5a3e690fc75d2cb69d29b62f826a7ddfcbb297619a90eb1d0b632fe6fd6dc666f0515120117f2479d25ca7ebd770f29bdcfb953b78e24c4e16a9d7cb5f429ad864a75d07ad04417db65e48c2615ff309caa8465ef11fda851bb7bd2370ba9aeea492afb8eff24276f49afdfc3a348a94a9ef8cb76d31b0272ad9677261e78a0e3dbe65f1da769b05ffae0957072aa3f2e49c824cf99d400f45e7ad3ff4887e7188b84d69dd9a768bdd9b1506048b7491b61fce3b7d28dda027057c6e339bc935a7e24c436bb5a80a22a71";
    	//1024
        String str1024 = "b6269fe239fcc7f1e681b1bf68633c1dd6258593f0f9f931cdbfedd1c9d46f5cbf6950dd4cbd82a091d964c71db27cd2a448820aad6bb877e9d8297e48bb35dc5f8ed85f36de39fa08e9b9536a9281b5f46cbef85ffd1b790b5dd8f16ac3af46f4807c2f4ea104e18bcc34a0f9f3c4fb6a3205b541cf165c126962d83c1d592d";
        System.out.println("str = " + str.length());
        int m = str.length() / 2;

        if (m * 2 < str.length()) {
            m++;
        }
        String[] strs = new String[m];
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                strs[j] = "" + str.charAt(i);
            } else {
                strs[j] = "0x" + strs[j] + "" + str.charAt(i);
                j++;
            }
        }
        //System.out.println(Arrays.toString(strs));
        System.out.println(strs.length);
        for (int i = 0; i < str.length(); i++) {
            if(i == 0 || i % 16 == 0){
                System.out.println();
            }
            if(i == 256){
                break;
            }
            System.out.print(strs[i] + ", ");

        }
    }
}

