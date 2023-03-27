package com.example.springboot3.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import io.jsonwebtoken.io.IOException;

public class Graphics2DExample {
	static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static void main(String[] args) throws IOException, java.io.IOException {
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/static/hinh-anh-lang-que-viet-nam-01.jpg"));
        BufferedImage outputImage = resizeImage(originalImage, originalImage.getWidth(), originalImage.getHeight());
        ImageIO.write(outputImage, "jpg", new File("src/main/resources/static/sampleImage-resized-graphics2d.jpg"));
    }
}
