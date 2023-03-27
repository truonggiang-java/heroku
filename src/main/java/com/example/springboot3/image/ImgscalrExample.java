package com.example.springboot3.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ImgscalrExample {
	public static BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, targetWidth);
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }

    public static void main(String[] args) throws Exception {
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/static/7498834410_85a1451f54_b.jpg"));
        BufferedImage outputImage = resizeImage(originalImage, originalImage.getWidth(), originalImage.getHeight());
        ImageIO.write(outputImage, "jpg", new File("src/main/resources/static/image-resized-imgscalr.jpg"));
    }
}
