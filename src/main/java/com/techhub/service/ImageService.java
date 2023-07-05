package com.techhub.service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public interface ImageService {
    String getImageUrl(String name);

    String save(MultipartFile file) throws IOException;

    String save(BufferedImage bufferedImage, String originalFileName) throws IOException;

    void delete(String name) throws IOException;

    default String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID() + getExtension(originalFileName);
    }

    default byte[] getByteArrays(BufferedImage bufferedImage, String format) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(bufferedImage, format, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
