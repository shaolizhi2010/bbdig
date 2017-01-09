package com.bbdig.core.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ImgUtil {

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	private static Logger logger = Logger.getLogger(ImgUtil.class);
	
	/*上传图片最大值*/
	public static int uploadImgMaxSizeM = 5;
	public static int uploadImgMaxSize = uploadImgMaxSizeM * 1024 * 1024;
	
	private Font font = new Font("微软雅黑", Font.BOLD, 18);// 添加字体的属性设置
	private int fontsize = 0;

	private Graphics2D g = null;

	/**
	 * 检查上传图片大小
	 * @param fileSize
	 * @return
	 */
	public static boolean checkMaxUploadImgSize(long fileSize){
		if(fileSize> uploadImgMaxSize){
			return false;
		}
		return true;
	}
	
	/**
	 * 设定文字的字体等
	 */
	public void setFont(String fontStyle, int fontSize) {
		this.fontsize = fontSize;
		this.font = new Font(fontStyle, Font.BOLD, fontSize);

	}

	public BufferedImage genErWeiMa(String url, int width, int height) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.MARGIN, 0);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
		BitMatrix bitMatrix = null;

		try {
			bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints);
			bitMatrix = updateMargin(bitMatrix, 8);  //设置margin n像素
			
			BufferedImage small = toBufferedImage(bitMatrix);
			
			small = zoomInImage(small,width,height);//根据size放大、缩小生成的二维码
			
			return small;
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private BitMatrix updateMargin(BitMatrix matrix, int margin){
        int tempM = margin*2;
       int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
            int resWidth = rec[2] + tempM;
            int resHeight = rec[3] + tempM;
            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
            resMatrix.clear();
        for(int i= margin; i < resWidth- margin; i++){   //循环，将二维码图案绘制到新的bitMatrix中
            for(int j=margin; j < resHeight-margin; j++){
                if(matrix.get(i-margin + rec[0], j-margin + rec[1])){
                    resMatrix.set(i,j);
                }
            }
        }
         return resMatrix;
    }



  /**
     * 图片放大缩小
     */
    public static BufferedImage  zoomInImage(BufferedImage  originalImage, int width, int height){
        BufferedImage newImage = new BufferedImage(width,height,originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0,0,width,height,null);
        g.dispose();
        return newImage;

    }

	private   BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 导入本地图片到缓冲区
	 */
	public BufferedImage loadImageLocal(String imgName) {
		try {
			return ImageIO.read(new File(imgName));
		} catch (IOException e) {

			logger.debug("读取图片失败 ： 图片地址 : " + imgName);
			logger.debug(e.getMessage());
			// System.out.println(e.getMessage());

		}
		return null;
	}

	/**
	 * 在图片中写一行字
	 * 
	 * @param img
	 * @param str
	 * @param x
	 * @param y
	 * @return
	 */
	public BufferedImage writeString(BufferedImage img, String str, int x, int y) {

		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			// g.setBackground(Color.WHITE);
			g.setColor(new Color(228, 96, 37)); // 设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			g.drawString(str, x, y);
			g.dispose();
		} catch (Exception e) {
			logger.debug("向图片中写入文字失败 ：文字 : " + str);
			logger.debug(e.getMessage());
		}

		return img;
	}

	/**
	 * 生成新图片到本地
	 */
	public static void writeImageLocal(String newImage, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				if(outputfile.exists()==false){
					outputfile.createNewFile();
				}
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 生成新图片到本地
	 */
	public static void writeImageLocal(String newImage, String imgType, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				if(outputfile.exists()==false){
					outputfile.createNewFile();
				}
				ImageIO.write(img, imgType, outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * 把 smallImg 和并到 bigImg里
	 * 
	 * @param smallImg
	 * @param bigImg
	 * @return
	 */
	public BufferedImage mergeImage(BufferedImage bigImg, BufferedImage smallImg, int x, int y) {

		try {
			int width = smallImg.getWidth();
			int height = smallImg.getHeight();

			g = bigImg.createGraphics();
			g.drawImage(smallImg, x, y, width, height, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return bigImg;
	}

	/**
	 * 按比例放大缩小
	 * 
	 * @param bigImg
	 * @param smallImg
	 * @param x
	 * @param y
	 * @return
	 */
	public BufferedImage scaleImage(BufferedImage img, int width, int height) {

		float xscale = (float) width / (float) img.getWidth();
		float yscale = (float) height / (float) img.getHeight();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(xscale, yscale);

		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D gd2 = outputImage.createGraphics();
		gd2.drawImage(img, affineTransform, null);
		gd2.dispose();
		// AffineTransformOp affineTransformOp = new
		// AffineTransformOp(affineTransform, null);
		// affineTransformOp.filter(bi, outputImage);

		return outputImage;

	}

	/**
	 * 将两张图片垂直方向合并
	 * @param imgup
	 * @param imgdown
	 * @return
	 */
	public BufferedImage unionImageVertical(BufferedImage imgup,BufferedImage imgdown){
		
		BufferedImage image = new BufferedImage(imgup.getWidth(), imgup.getHeight()+imgdown.getHeight(), BufferedImage.TYPE_INT_RGB);
		image = mergeImage(image, imgup, 0, 0);
		image = mergeImage(image, imgdown, 0, imgup.getHeight());
		return image;
	}
	
	public static void main(String[] args) throws Exception {
		
		
		

		ImgUtil imgUtil = new ImgUtil();
		
		BufferedImage erweima = imgUtil.genErWeiMa("http://baidu.com", 138, 138);
		
		imgUtil.writeImageLocal("d:/test/er.png", erweima);
		
		/*
		int fontsize = 18;
		imgUtil.setFont("微软雅黑", fontsize);

		BufferedImage big = imgUtil.loadImageLocal("d:/test/big.png");
		BufferedImage small = imgUtil.loadImageLocal("d:/test/small.png");

		// scale
		BufferedImage small2 = imgUtil.scaleImage(small, 134, 134);
		// imgUtil.writeImageLocal("d:/test/small2.png", small2);

		// imgUtil.writeImageLocal("d:/test/result.png", temp);

		BufferedImage head = imgUtil.loadImageLocal("d:/test/head12345.png");
		// scale
		BufferedImage head2 = imgUtil.scaleImage(head, 66, 66);

		BufferedImage result = imgUtil.mergeImage(big, small2, 252, 408);

		// result = imgUtil.mergeImage(big, small2, 246,
		// 355);

		result = imgUtil.mergeImage(big, head2, 110, 48);

		result = imgUtil.writeString(result, "大树", 222, 70);

		imgUtil.writeImageLocal("d:/test/result.png", result);

		System.out.println("end");

		// InputStream imagein=new FileInputStream("图片一");
		// InputStream imagein2=new FileInputStream("图片二");
		// BufferedImage image=ImageIO.read(imagein);
		// BufferedImage image2=ImageIO.read(imagein2);
		// Graphics g=image.getGraphics();
		// g.drawImage(image2,image.getWidth()-image2.getWidth(),image.getHeight()-image2.getHeight(),null);
		// OutputStream out=new FileOutputStream("合并后图片");
		// JPEGImageEncoder enc=JPEGCodec.createJPEGEncoder(out);
		// enc.encode(image);
		// imagein.close();
		// imagein2.close();
		// out.close();
 
		 */
	}
}
