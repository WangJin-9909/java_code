
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.utils.FileTool;
import com.utils.HttpsDownloadUtils;

public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println("文件测试");

		String currentDir = FileTool.getCurrentDir();
		String url = "https://129.28.184.240:8443/directory/10.jpeg";
		String saveDir = currentDir;
		String fileName = "/temp.jpeg";
		// FileTool.download(url, saveDir, fileName);
		HttpsDownloadUtils.downloadFile(url, saveDir + fileName);

	}

	public void test() throws IOException {
		String url = "https://129.28.184.240:8443/directory/10.jpeg";
		URL Url = new URL(url);
		InputStream openStream = Url.openStream();

	}

	

}
