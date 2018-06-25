package com.api.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ImageUtil {

    public static String[] getImages(String images, String split) {
        if (images == null || images.equals("")) {
            return new String[]{};
        } else {
            return Arrays.stream(images.split(split)).map((img) -> "/api/files/300_200/" + img ).toArray(String[]::new);
        }
    }

    public static byte[] compress(InputStream is, String fileType, int width, int height) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        BufferedImage src = ImageIO.read(is);
        int picWidth = src.getWidth();   //得到图片的宽度
        int picHeight = src.getHeight();  //得到图片的高度
        float ratio = scale(picWidth, picHeight, width, height);
        int nWidth = (int)(picWidth * ratio);
        int nHeight = (int)(picHeight * ratio);
        Image image = src.getScaledInstance(nWidth, nHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(image, 0, 0, null);
        outputImage.getGraphics().dispose();

        ImageIO.write(outputImage, fileType, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static float scale(int imageWidth, int imageHeight, int bitWidth, int bitHeight) {
        float ratio = 1;
        // Log.e("imageWidth:", imageWidth + ", imageHeight:" + imageHeight + ",bitWidth: " + bitWidth + ",bitHeight" + bitHeight);
        if (imageWidth > imageHeight && imageWidth > bitWidth) {
            ratio = (float)bitWidth / imageWidth;
        } else if (imageWidth < imageHeight && imageHeight > bitHeight) {
            ratio = (float)bitHeight / imageHeight;
        }
        return ratio;
    }

}
