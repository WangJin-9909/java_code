/**
* @author Dabing
* @E-mail chenbinging@outlook.com
* @date 2018-10-18涓嬪崍4:21:15
* 鍥剧墖澶勭悊绫�
*/
package example.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import example.LatticeSegmentationBean;





public class ImageUtils {
	
	// 鍥剧墖鐨勫搴�
//    private static final int CAPTCHA_WIDTH = 300;
    // 鍥剧墖鐨勯珮搴�
//    private static final int CAPTCHA_HEIGHT = 100;
    // 楠岃瘉鐮佺殑涓暟
//    private static final int CAPTCHA_CODECOUNT = 4;
//	private static final int CAPTCHA_FONT_HEIGHT = CAPTCHA_HEIGHT/5; //18;
//    private static final int CAPTCHA_CODE_Y = CAPTCHA_HEIGHT/2; //16;
    private static final char[] codeSequence = { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 
        'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    /**
     * 鑾峰彇鍥剧墖buffer
     * @param path
     * @return
     */
	public BufferedImage getImageBuffer(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		File file = new File(path);
        FileInputStream inputStream = null;
        BufferedImage imageQR = null;
		try {
			inputStream = new FileInputStream(file);
			imageQR = ImageIO.read(inputStream);
			inputStream.close();
			inputStream = null;
		} catch (Exception e) {
			return null;
		}
		return imageQR;
	}
	
	/**
	 * 浠庡浘鐗囦腑闅忔満鐢熸垚婊戝姩楠岃瘉鐮佺殑鍧愭爣鐐�
	 * @param imageQR
	 * @return
	 */
	public int[] randomPoint (BufferedImage imageQR) {
		int width = imageQR.getWidth();
        int height = imageQR.getHeight();
        
        int rWidth = 0;
        int rHeight = 0;
        if (width < 60 || height < 60) {
        	return new int[]{10, 10};
    	} else if (width < 150 || height < 100) {
    		return new int[]{80, 10};
    	} else {
    		Random random = new Random();
    		do {
    			rWidth = random.nextInt(width);
    		} while (rWidth < 60 || rWidth > width -70);
    		do {
    			rHeight = random.nextInt(height);
    		} while (rHeight < 10 || rHeight > height -70);
    		
    		return new int[]{rWidth, rHeight};
    	}
	}
	
	public int[] randomPoint (int w1, int h1, int w2, int h2) {
        int rWidth = 0;
        int rHeight = 0;
        if (w1 < 60 || h1 < 60) {
        	return new int[]{10, 10};
    	} else if (w1 < 150 || h1 < 100) {
    		return new int[]{80, 10};
    	} else {
    		Random random = new Random();
    		do {
    			rWidth = random.nextInt(w1);
    		} while (rWidth < w2 || rWidth > w1 - w2);
    		do {
    			rHeight = random.nextInt(h1);
    		} while (rHeight < 30 || rHeight > h1 - h2);
    		
    		return new int[]{rWidth, rHeight};
    	}
	}
	
	/**
	 * 浠庡浘鐗囦腑闅忔満鐢熸垚鍥炬枃楠岃瘉鐮佺殑鍧愭爣鐐�
	 * @param imageQR 鍥剧墖璧勬簮
	 * @param count 鏁伴噺
	 * @return
	 */
	public ArrayList<LatticeSegmentationBean> randomPoint (int width, int height, int count) {
//        ArrayList<LatticeSegmentationBean> rangeList = new ArrayList<>();
//        int[] m_xRangePoint = new int[count];
//        int[] m_yRangePoint = new int[count];
//        int t = 0;
//        for (int i = 0; i < count; i++) {
//        	for (int j = 0; j < count; j++) {
//        		rangeList.add(t, new LatticeSegmentationBean(width * i / count + width / count / 2, height * j / count + height / count / 2));
//        		t++;
//			}
        	/*if( i == 0) {
//        		m_xRangePoint[0] = 0;
//        		m_yRangePoint[0] = 0;
        		rangeList.add(i, new LatticeSegmentationBean(0, 0));
        		continue;
        	}
//        	m_xRangePoint[i] = width * i / count;
//        	m_yRangePoint[i] = height * i / count;
        	rangeList.add(i, new LatticeSegmentationBean(width * i / count, height * i / count));*/
//		}
        
        ArrayList<LatticeSegmentationBean> m_tempAry = new ArrayList<LatticeSegmentationBean>();
        
        int index = 0;
        while (index < count) {
        	Random r = new Random();
//        	int listSize = rangeList.size();
//        	int temp = r.nextInt(listSize);
        	int tempX = 0;
        	int tempY = 0;
        	tempX = randomInt(width);
        	tempY = randomInt(height);
        	
//        	LatticeSegmentationBean lsObject = rangeList.get(temp);
//        	int i1 = m_xRangePoint[temp];
//        	int x = lsObject.getM_xRangePoint();
//        	temp = r.nextInt(count);
//        	int i2 = m_yRangePoint[temp];
//        	int y = lsObject.getM_yRangePoint();
//        	String s1 = x + "||" + y;

        	boolean flag = false;
        	for (LatticeSegmentationBean bean : m_tempAry) {
        		if (bean.getM_xRangePoint() > (tempX - 40) && bean.getM_xRangePoint() < (tempX + 40) && bean.getM_yRangePoint() > (tempY - 40) && bean.getM_yRangePoint() < (tempY + 40)) {
        			flag = true;
        			break;
        		}
        		/*if (bean.getM_xRangePoint() == x && bean.getM_yRangePoint() == y) {
        			flag = true;
        			break;
        		}*/
    		}
        	if (!flag) {
        		LatticeSegmentationBean lsObject = new LatticeSegmentationBean(tempX, tempY);
        		m_tempAry.add(lsObject);
        		index++;
        	}
        }
        
        return m_tempAry;
	}
	
	/**
	 * 澶勭悊鍥剧墖锛岀粯鍒跺共鎵扮嚎
	 * @param inImage
	 * @return
	 */
	public BufferedImage CreateImage(BufferedImage inImage) {
	    // 瀹氫箟鍥惧儚 Buffer
	    int CAPTCHA_WIDTH = inImage.getWidth();
	    // 鍥剧墖鐨勯珮搴�
	    int CAPTCHA_HEIGHT = inImage.getHeight();
	    int CAPTCHA_FONT_HEIGHT = CAPTCHA_HEIGHT/5; //18;
	    int CAPTCHA_CODE_Y = CAPTCHA_HEIGHT/2; //16;
	    BufferedImage buffImg = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB); 
	    // 鍒涘缓涓�涓粯鍒跺浘鍍忕殑瀵硅薄
	    Graphics2D g = buffImg.createGraphics();
	    // 鍒涘缓涓�涓殢鏈烘暟鐢熸垚鍣ㄧ被
	    Random random = new Random();
	    // 灏嗗浘鍍忓～鍏呬负鐧借壊
	    g.setColor(Color.WHITE);
//	    g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
	    g.drawImage(inImage, 0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT, null);
	    // 璁剧疆瀛椾綋
//	    g.setFont(new Font("Fixedsys", Font.BOLD, CAPTCHA_FONT_HEIGHT)); 
	    // 璁剧疆瀛椾綋杈圭紭鍏夋粦
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	    // 鐢昏竟妗� 
//	    g.setColor(Color.BLACK);
//	    g.drawRect(0, 0, CAPTCHA_WIDTH - 1, CAPTCHA_HEIGHT - 1);
	    // 闅忔満浜х敓40鏉″共鎵扮嚎锛屼娇鍥捐薄涓殑璁よ瘉鐮佷笉鏄撹鍏跺畠绋嬪簭鎺㈡祴鍒般��  
	    int red = 0, green = 0, blue = 0; 
	    for (int i = 0; i < 20; i++) {
	    	red = random.nextInt(255); 
	        green = random.nextInt(255); 
	        blue = random.nextInt(255);
	        g.setColor(new Color(red, green, blue));
	        int x = random.nextInt(CAPTCHA_WIDTH); 
	        int y = random.nextInt(CAPTCHA_HEIGHT); 
	        int xl = 0;
	        int yl = 0;
	        if(i % 2 == 0) {
	        	xl = x + random.nextInt(80); 
		        yl = y + random.nextInt(80);
	        } else {
	        	xl = x + random.nextInt(80); 
		        yl = y - random.nextInt(80);
	        }
	        g.drawLine(x, y, xl, yl); 
	    } 
//	    g.setColor(Color.BLACK);
//	    Rectangle2D rect2 = new Rectangle2D.Double(60, 160, 60, 90);
//	    g.draw(rect2);
//	    g.fillRect(60, 160, 60, 90);
	    // 淇濆瓨闅忔満浜х敓鐨勯獙璇佺爜锛屼互渚跨敤鎴风櫥褰曞悗杩涜楠岃瘉
//	    StringBuffer randomCode = new StringBuffer(); 
	    // 闅忔満浜х敓楠岃瘉鐮�  
	    /*for (int i = 0; i < CAPTCHA_CODECOUNT; i++) { 
	        // 寰楀埌闅忔満浜х敓鐨勯獙璇佺爜鏁板瓧
	        String code = String.valueOf(codeSequence[random.nextInt(36)]); 
	        // 浜х敓闅忔満鐨勯鑹插垎閲忔潵鏋勯�犻鑹插�硷紝杩欐牱杈撳嚭鐨勬瘡浣嶆暟瀛楃殑棰滆壊鍊奸兘灏嗕笉鍚�  
	        red = random.nextInt(255); 
	        green = random.nextInt(255); 
	        blue = random.nextInt(255); 
	        // 鐢ㄩ殢鏈轰骇鐢熺殑棰滆壊灏嗛獙璇佺爜缁樺埗鍒板浘鍍忎腑  
	        g.setColor(new Color(red, green, blue)); 
	        int x = random.nextInt(CAPTCHA_WIDTH - CAPTCHA_FONT_HEIGHT);
	        int y = random.nextInt(CAPTCHA_HEIGHT - CAPTCHA_FONT_HEIGHT) + CAPTCHA_FONT_HEIGHT;
	        g.drawString(code, x, y); 
	        // 灏嗕骇鐢熺殑闅忔満鏁扮粍鍚堝湪涓�璧�
//	        randomCode.append(code); 
	    }*/
	    g.dispose();
	    return buffImg;
	}
	
	/**
	 * 灏嗗浘鐗嘼uffer杈撳叆鍒版湰鍦版枃浠朵腑
	 * @param bufferedImage
	 * @param path
	 * @param formatName
	 */
	public void BufferedImageToFile(BufferedImage bufferedImage, String path, String formatName) {
		if (bufferedImage == null) {
			return;
		}
		if (StringUtils.isEmpty(path) || StringUtils.isEmpty(formatName)) {
			return;
		}
        try {
			ImageIO.write(bufferedImage, formatName, new File(path + "." + formatName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CreateCaptcha(String path) {
        //闇�瑕佹坊鍔犵殑鍥剧墖
        String imageQRUrl = "C://Users//chenb//Documents//Tencent Files//348568466//FileRecv//demo.jpeg";
        //淇濆瓨鍥剧墖鐨勮矾寰�
        String result = "C://Users//chenb//Documents//Tencent Files//348568466//FileRecv//aaa//";
        File file = new File(imageQRUrl);
        FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			FileChannel fileChannel = inputStream.getChannel();
			SysLog.println("size:" + fileChannel.size());
			SysLog.println("length:" + file.length()/1024);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BufferedImage imageQR = null;
        try {
			imageQR = ImageIO.read(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        SysLog.println("width:" + imageQR.getWidth());
        SysLog.println("height:" + imageQR.getHeight());
        /*result += "1" + ".jpg";
        int temp = result.lastIndexOf(".") + 1;
        try {
			ImageIO.write(imageQR, result.substring(temp), new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	private int randomInt (int i) {
		Random r = new Random();
		int temp = 0;
    	do {
    		temp = r.nextInt(i);
		} while (temp < 40 || temp > i -40);
    	
    	return temp;
	}
	
	public GeneralPath drawPath(int blockWidth, int blockHeight) {
		float sw = blockWidth / 5;
		float sh = blockHeight / 5;
		Random random = new Random();
		int[] circle = new int[4];
		for (int i = 0; i < 4; i++) {
			int t = random.nextInt(3);
			if(t == 0) {
				circle[i] = 0;
			} else if (t == 1){
				circle[i] = 1;
			} else {
				circle[i] = 2;
			}
		}
		GeneralPath gp = new GeneralPath();
		gp.moveTo(sw, sh);
		gp.lineTo(sw * 2, sh);
		if (circle[0] == 0) {
			gp.quadTo(blockWidth / 2, sh * 1 / 3, sw * 3, sh);
		} else if (circle[0] == 1){
			gp.quadTo(blockWidth / 2, sh + sh * 2 / 3, sw * 3, sh);
		} else {
			gp.lineTo(sw * 3, sh);
		}
		gp.lineTo(sw * 4, sh);
		gp.lineTo(sw * 4, sh * 2);
		if (circle[1] == 0) {
			gp.quadTo(sw * 4 + sw * 2 / 3, blockHeight / 2, sw * 4, sh * 3);
		} else if (circle[1] == 1){
			gp.quadTo(sw * 3 + sw * 1 / 3, blockHeight / 2, sw * 4, sh * 3);
		} else {
			gp.lineTo(sw * 4, sh * 3);
		}
		gp.lineTo(sw * 4, sh * 4);
		gp.lineTo(sw * 3, sh * 4);
		if (circle[2] == 0) {
			gp.quadTo(blockWidth / 2, blockHeight - sh * 1 / 3, sw * 2, sh * 4);
		} else if (circle[2] == 1){
			gp.quadTo(blockWidth / 2, sh * 3 + sh * 1 / 3, sw * 2, sh * 4);
		} else {
			gp.lineTo(sw * 2, sh * 4);
		}
		gp.lineTo(sw, sh * 4);
		gp.lineTo(sw, sh * 3);
		if (circle[3] == 0) {
			gp.quadTo(sw * 1 / 3, blockHeight / 2, sw, sh * 2);
		} else if (circle[3] == 1){
			gp.quadTo(sw + sw * 2 / 3, blockHeight / 2, sw, sh * 2);
		} else {
			gp.lineTo(sw, sh * 2);
		}
		
		gp.closePath();
		
		return gp;
	}
	
	public BufferedImage DealOriPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x,
            int y){
		// 婧愭枃浠跺浠藉浘鍍忕煩闃� 鏀寔alpha閫氶亾鐨剅gb鍥惧儚
        BufferedImage ori_copy_image = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 婧愭枃浠跺浘鍍忕煩闃�
        int[][] oriImageData = null;
		try {
			oriImageData = getData(oriImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
        // 妯℃澘鍥惧儚鐭╅樀
        int[][] templateImageData = null;
		try {
			templateImageData = getData(templateImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		if (oriImageData == null || templateImageData == null) {
			return null;
		}
        for (int i = 0; i < oriImageData.length; i++) {
            for (int j = 0; j < oriImageData[0].length; j++) {
                int rgb = oriImage.getRGB(i, j);
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                ori_copy_image.setRGB(i, j, rgb);
            }
        }
 
        Random random = new Random();
        int tx = random.nextInt(x - 10);
        int ty = random.nextInt(oriImage.getHeight() - templateImage.getHeight());
        for (int i = 0; i < templateImageData.length; i++) {
            for (int j = 0; j < templateImageData[0].length - 5; j++) {
                int rgb = templateImage.getRGB(i, j);
                if (rgb != 16777215 && rgb < 0) {
                    int rgb_ori = ori_copy_image.getRGB(x + i, y + j);
                    int r = (int) ((0xff & rgb_ori) * 0.5);
                    int g = (int) ((0xff & (rgb_ori >> 8)) * 0.5);
                    int b = (int) ((0xff & (rgb_ori >> 16)) * 0.5);
                    rgb_ori = r + (g << 8) + (b << 16) + (255 << 24);
                    ori_copy_image.setRGB(x + i, y + j, rgb_ori);
                    
                    // 娣锋穯妯″潡
                    rgb_ori = ori_copy_image.getRGB(tx + i, ty + j);
                    r = (int) ((0xff & rgb_ori) * 0.7);
                    g = (int) ((0xff & (rgb_ori >> 8)) * 0.7);
                    b = (int) ((0xff & (rgb_ori >> 16)) * 0.7);
                    rgb_ori = r + (g << 8) + (b << 16) + (255 << 24);
                    ori_copy_image.setRGB(tx + i, ty + j, rgb_ori);
                } else {
                }
            }
        }
        
        return ori_copy_image;
	}
	
	public BufferedImage DealBlockPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x,
            int y){
		// 婧愭枃浠跺浠藉浘鍍忕煩闃� 鏀寔alpha閫氶亾鐨剅gb鍥惧儚
        BufferedImage ori_copy_image = new BufferedImage(templateImage.getWidth(), templateImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 妯℃澘鍥惧儚鐭╅樀
        int[][] templateImageData = null;
		try {
			templateImageData = getData(templateImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		if (templateImageData == null) {
			return null;
		}
		
        for (int i = 0; i < templateImageData.length; i++) {
            for (int j = 0; j < templateImageData[0].length - 5; j++) {
                int rgb = templateImage.getRGB(i, j);
                if (rgb != 16777215 && rgb < 0) {
                	int rgb1 = templateImage.getRGB(i - 1, j);
                	int rgb2 = templateImage.getRGB(i + 1, j);
                	int rgb3 = templateImage.getRGB(i, j - 1);
                	int rgb4 = templateImage.getRGB(i, j + 1);
                	if (rgb1 == 16777215 || rgb1 >= 0 || rgb3 == 16777215 || rgb3 >= 0) {
                		ori_copy_image.setRGB(i, j, Color.white.getRGB());
                	} else if (rgb2 == 16777215 || rgb2 >= 0 || rgb4 == 16777215 || rgb4 >= 0){
                		ori_copy_image.setRGB(i, j, Color.black.getRGB());
                	} else {
                		int rgb_ori = oriImage.getRGB(x + i, y + j);
                		ori_copy_image.setRGB(i, j, rgb_ori);
                	}
                } else {
//                	ori_copy_image.setRGB(i, j, rgb);
                }
            }
        }
        
        return ori_copy_image;
	}
	
	public BufferedImage copyBufferImage(BufferedImage src) {
		BufferedImage ori_copy_image = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int[][] oriImageData = null;
		try {
			oriImageData = getData(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < oriImageData.length; i++) {
	        for (int j = 0; j < oriImageData[0].length; j++) {
	            int rgb = src.getRGB(i, j);
	            int r = (0xff & rgb);
	            int g = (0xff & (rgb >> 8));
	            int b = (0xff & (rgb >> 16));
	            ori_copy_image.setRGB(i, j, rgb);
	        }
    	}
		
		return ori_copy_image;
	}
	
	private int[][] getData(BufferedImage bimg) throws Exception {
        int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
        for (int i = 0; i < bimg.getWidth(); i++) {
            for (int j = 0; j < bimg.getHeight(); j++) {
                data[i][j] = bimg.getRGB(i, j);
            }
        }
        return data;
    }
}
