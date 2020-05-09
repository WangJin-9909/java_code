package example;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PicDemo {
	private static void splitImage(String originalImg) throws IOException {
		 
        //String originalImg = "C:\\img\\split\\a380_1280x1024.jpg";
        //String originalImg = "D:\\img\\demo.jpeg";
        // �����ͼ
        File file = new File(originalImg);
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);
 

        int rows = 2;//��
        int cols = 3;//��
        int chunks = rows * cols;
 
        // ����ÿ��Сͼ�Ŀ�Ⱥ͸߶�
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;
 
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //����Сͼ�Ĵ�С������
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
 
                //д��ͼ������
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0,
                        chunkWidth, chunkHeight,
                        chunkWidth* y, chunkHeight * x,
                        chunkWidth * y + chunkWidth,
                        chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
 
        // ���Сͼ
        for (int i = 0; i < imgs.length; i++) {
            //ImageIO.write(imgs[i], "jpg", new File("C:\\img\\split\\img" + i + ".jpg"));
            ImageIO.write(imgs[i], "jpg", new File("d:\\img\\img" + i + ".jpg"));
        }
 
        System.out.println("��ɷָ");
    }

	
	
	public static void main(String[] args) throws IOException {
		splitImage();
	}
}
