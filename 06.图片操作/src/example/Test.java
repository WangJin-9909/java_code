package example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.imageio.ImageIO;

import example.utils.ImageUtils;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("D:\\apache-tomcat-7.0.53\\webapps\\TestHttpProtocolNew\\demo.jpeg");
		FileInputStream fis = new FileInputStream(file);
		String uuid2 = "111";
		Test test = new Test();
		byte[] slideBackgroundStream = test.getSlideBackgroundStream(uuid2, fis);
		System.out.println(slideBackgroundStream.length);

	}

	public byte[] getSlideBackgroundStream(String uuid, FileInputStream iStream) {
		byte[] bt = null;
		BufferedImage blockBuf = null;
		ImageUtils imUtils = new ImageUtils();
		GeneralPath gp = null;

		BufferedImage imageQr = null;
		try {
			// RandomItem item = c_manager.getRandomCode(uuid);
			/*
			 * if (item == null) { return bt; }
			 */
			int width = 300;// item.getWidth();
			int height = 200;// item.getHeight();
			int blockW = 300;// item.getBlockW();
			int blockH = 200;// item.getBlockH();

			// 处理图片的拉伸效果
			imageQr = ImageIO.read(iStream);

			int imageQrWidth = imageQr.getWidth();
			int imageQrHeight = imageQr.getHeight();
			Image img = imageQr.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

			double wr = width * 1.0 / imageQrWidth;
			double hr = height * 1.0 / imageQrHeight;
			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);

			img = ato.filter(imageQr, null);

			// 绘制滑块区域图像
			blockBuf = new BufferedImage(blockW, blockH, BufferedImage.TYPE_INT_ARGB);
			//gp = item.getPath();
			Graphics2D g = blockBuf.createGraphics();
			g.setColor(Color.black);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			
			//g.fill(gp);
			g.dispose();

			//int[] ri = item.getrPoint();
			int bw = 40;// ri[0] - blockW / 2;
			int bh = 60;//ri[1] - blockH / 2;
			BufferedImage dealOriPicture = imUtils.DealOriPictureByTemplate((BufferedImage) img, blockBuf, bw, bh);

			ByteArrayOutputStream os = new ByteArrayOutputStream();// 新建流。
			try {
				// 利用ImageIO类提供的write方法，将by以png图片的数据模式写入流。
				ImageIO.write(dealOriPicture, "png", os);
			} catch (IOException e) {
				e.printStackTrace();
			}
			bt = os.toByteArray();// 从流中获取数据数组。
			os.close();
			//item = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			iStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}

}
