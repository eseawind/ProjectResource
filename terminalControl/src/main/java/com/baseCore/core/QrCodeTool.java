package com.baseCore.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeTool {
	private static Logger logger=LoggerFactory.getLogger(QrCodeTool.class);
	//定义颜色
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	/**
	 * 图片格式定义
	 * 
	 * @value Array
	 */
	private static String[] IMAGE_TYPE = { "BMP", "bmp", "jpg", "JPG", "wbmp", "jpeg", "png", "PNG", "JPEG", "WBMP",
			"GIF", "gif", "ICON", "icon" };

	/**
	 * 二维码宽度
	 */
	public static final int WIDTH = 260;

	/**
	 * 二维码高度
	 */
	public static final int HEIGHT = 260;

	/**
	 * 图标宽度
	 */
	private static final int IMAGE_WIDTH = 68;
	/**
	 * 图标高度
	 */
	private static final int IMAGE_HEIGHT = 68;
	/**
	 * 底图大小【正方形】
	 */
	private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
	/**
	 * 底图边框
	 */
	private static final int FRAME_WIDTH = 5;

	/**
	 * 二维码写码器
	 */
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

	/**
	 * 二维码生成-方法入口
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param iconImagePath
	 *            图标原路径
	 * @param qrcodeImagePath
	 *            二维码存放路径
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白
	 * @return 成功：返回输出图片绝对路径；失败：返回null
	 * @throws Exception 
	 */
	public static String encode(String content, int width, int height, String iconImagePath, String qrcodeImagePath,
			boolean hasFiller) throws Exception {
		try {
			/**
			 * 图标格式校验
			 */
			File icon = new File(iconImagePath);
			if (!icon.exists()) {
				logger.error("系统找不到图标所在文件 ！将生成无图标二维码");
				writeToFile(width, height, content, "jpg", new File(iconImagePath));
			}
			String iconFileName = icon.getName();
			// 得到上传文件的扩展名
			String fileExtName = iconFileName.substring(iconFileName.lastIndexOf(".") + 1);
			if (!checkIMGType(fileExtName)) {
				logger.error("图标格式有误 ！");
				return null;
			}

			if (width < 260 || height < 260) {
				width = QrCodeTool.WIDTH;
				height = QrCodeTool.HEIGHT;
			}
			ImageIO.write(genBarcode(content, width, height, iconImagePath, hasFiller), "png",
					new File(qrcodeImagePath));
			logger.info("二维码已生成  " + qrcodeImagePath);
			return qrcodeImagePath;
		} catch (IOException e) {
			logger.error("图片读取异常 ： " + e.getMessage());
		} catch (WriterException e) {
			logger.error("图片输出异常 ：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 输出到流
	 * @param content
	 * @param width
	 * @param height
	 * @param iconImagePath
	 * @param stream
	 * @param hasFiller
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String content, int width, int height, String iconImagePath, OutputStream stream,
			boolean hasFiller) throws Exception {
		try {
			/**
			 * 图标格式校验
			 */
			File icon = new File(iconImagePath);
			if (!icon.exists()) {
				logger.error("系统找不到图标所在文件 ！将输出无logo二维码！");
				writeToStream(width, height, content, "jpg", stream);
			}
			String iconFileName = icon.getName();
			// 得到上传文件的扩展名
			String fileExtName = iconFileName.substring(iconFileName.lastIndexOf(".") + 1);
			if (!checkIMGType(fileExtName)) {
				logger.error("图标格式有误 ！");
				return null;
			}

			if (width < 260 || height < 260) {
				width = QrCodeTool.WIDTH;
				height = QrCodeTool.HEIGHT;
			}
			ImageIO.write(genBarcode(content, width, height, iconImagePath, hasFiller), "png",
					 stream);
		} catch (IOException e) {
			logger.error("图片读取异常 ： " + e.getMessage());
		} catch (WriterException e) {
			logger.error("图片输出异常 ：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 图片处理方法
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @param iconImagePath
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	private static BufferedImage genBarcode(String content, int width, int height, String iconImagePath,
			boolean hasFiller) throws WriterException, IOException {
		// 读取源图像
		BufferedImage scaleImage = scale(iconImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, hasFiller);
		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}

		BitMatrix matrix = setBitMatrix(width, height, content);
		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 读取图片
				if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH && y > halfH - IMAGE_HALF_WIDTH
						&& y < halfH + IMAGE_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
				}
				// 在图片四周形成边框
				else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
					pixels[y * width + x] = 0xfffffff;
				} else {
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? BLACK : WHITE;
				}
			}
		}

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);

		return image;
	}

	/**
	 * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
	 * 
	 * @param iconImagePath
	 *            小图标源文件地址
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	private static BufferedImage scale(String iconImagePath, int height, int width, boolean hasFiller)
			throws IOException {
		double ratio = 0.0; // 缩放比例
		File file = new File(iconImagePath);
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {// 补白
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null),
						destImage.getHeight(null), Color.white, null);
			else
				graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
						destImage.getHeight(null), Color.white, null);
			graphic.dispose();
			destImage = image;
			logger.info("INFO 图标补白已完成 ");
		}
		return (BufferedImage) destImage;
	}

	/**
	 * 图片格式校验
	 * 
	 * @param fileExtName
	 * @return
	 */
	private static boolean checkIMGType(String fileExtName) {
		boolean flag = false;
		for (String type : IMAGE_TYPE) {
			// -- 图片格式正确
			if (type.toLowerCase().equals(fileExtName.toLowerCase())) {
				flag = true;
				break;
			}
		}
		// ------------图片格式校验结束
		return flag;
	}
	
	
	
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
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
	 * 生成图片到本地
	 * @param matrix
	 * @return
	 */
	public static void writeToFile(int width,int height,String content,String format, File file) throws Exception {
		if (width < 260 || height < 260) {
			width = QrCodeTool.WIDTH;
			height = QrCodeTool.HEIGHT;
		}
		BitMatrix matrix = setBitMatrix(width, height, content);
		// 生成二维码
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("生产二维码格式异常 " + format + " to " + file);
		}
	}
	/**
	 * 生成图片到流
	 * @param matrix
	 * @return
	 */
	public static void writeToStream(int width,int height,String content, String format, OutputStream stream) throws Exception {
		if (width < 260 || height < 260) {
			width = QrCodeTool.WIDTH;
			height = QrCodeTool.HEIGHT;
		}
		BitMatrix matrix = setBitMatrix(width, height, content);
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("生产二维码格式异常 " + format);
		}
	}
	private static BitMatrix setBitMatrix(int width, int height, String content) throws WriterException {
		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
		return matrix;
	}
	/**
	 * 测试主方法入口
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String iconImagePath  = "C:/1.jpg";
		String codePath = "C:/new.jpg";
		/**
		 * 测试方法入口
		 */
		QrCodeTool.encode("MC5.2002.0519*DN*DV*SN5.2002.0519-09.170620C1.02730*MF*AG*DC", 300, 300, iconImagePath , codePath, true);
	}
}
