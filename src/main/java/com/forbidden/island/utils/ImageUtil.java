package com.forbidden.island.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageUtil {

    /**
     * 加载一张图片（原始尺寸）
     *
     * @param imageName 图片文件名，相对于资源路径 Constant.RESOURCES_PATH
     * @return Image 对象，加载失败返回 null
     */
    public static Image getImage(String imageName) {
        return new ImageIcon(Constant.RESOURCES_PATH + imageName).getImage();
    }

    /**
     * 加载一张指定尺寸的图片（会进行缩放）
     *
     * @param imageName   图片文件名
     * @param imageWidth  目标宽度
     * @param imageHeight 目标高度
     * @return 缩放后的 Image 对象
     */
    public static Image getImage(String imageName, int imageWidth, int imageHeight) {
        return new ImageIcon(Constant.RESOURCES_PATH + imageName)
                .getImage()
                .getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
    }

    /**
     * 加载一张图片，并进行旋转和缩放
     *
     * @param imageName     图片文件名
     * @param imageWidth    缩放后的宽度
     * @param imageHeight   缩放后的高度
     * @param rotationAngle 顺时针旋转角度（单位：度）
     * @return 处理后的 Image 对象（先旋转再缩放）
     */
    public static Image getImage(String imageName, int imageWidth, int imageHeight, double rotationAngle) {
        BufferedImage image = null;
        try {
            // 加载图片为 BufferedImage
            image = ImageIO.read(new File(Constant.RESOURCES_PATH + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果图片加载失败，返回 null
        if (image == null) {
            return null;
        }

        // 旋转图片
        BufferedImage bufferedImage = rotate(Objects.requireNonNull(image), rotationAngle);

        // 缩放旋转后的图片
        return new ImageIcon(bufferedImage).getImage().getScaledInstance(imageWidth, imageHeight, 4);
    }

    /**
     * 对 BufferedImage 进行旋转
     *
     * @param image   原始图片
     * @param degrees 顺时针旋转角度（单位：度）
     * @return 旋转后的 BufferedImage
     */
    public static BufferedImage rotate(BufferedImage image, Double degrees) {
        // 将角度转换为弧度
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        // 计算旋转后图片的新宽高
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        // 创建一个新的空白图像（支持透明）
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();

        // 抗锯齿与质量优化
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 计算旋转中心点（原图居中）
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;

        // 构建变换对象
        AffineTransform at = new AffineTransform();
        at.translate(x, y); // 移动图像到中心位置
        at.setToRotation(radians, x + (image.getWidth() / 2.0), y + (image.getHeight() / 2.0)); // 围绕图像中心旋转
        g2d.setTransform(at);

        // 设置变换并绘制原始图片
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }
}
