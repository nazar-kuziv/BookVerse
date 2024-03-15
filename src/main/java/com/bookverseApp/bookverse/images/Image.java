package com.bookverseApp.bookverse.images;

import com.bookverseApp.bookverse.exceptions.SomethingWentWrongException;
import com.bookverseApp.bookverse.exceptions.UnsupportedFileTypeException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

public class Image {
    private static Long imageId = 30L;

    private static final String uploadDir = "src/main/resources/static/images/";

    public static String loadUserImage(MultipartFile file, Long userId) {
        String mediaType = Image.checkMediaType(file);

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new java.util.Date());
        String fileName = imageId + "-" + userId + "-" + timeStamp + mediaType;
        String fileNameTest = imageId + "-" + userId + "-" + timeStamp;

        imageId++;

        Path uploadPath = Paths.get(uploadDir);

        try {
            Files.createDirectories(uploadPath);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Unable to create the directory to store uploaded files.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:8080/images/" + fileNameTest;
        } catch (Exception e) {
            throw new SomethingWentWrongException("Unable to save the uploaded file: " + fileName);
        }
    }

    public static String loadBookImage(MultipartFile file) {
        String mediaType = Image.checkMediaType(file);

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new java.util.Date());
        String randomId = String.valueOf((int) (Math.random() * 1000000));
        String fileName = imageId + "-" + timeStamp + "-" + randomId + mediaType;
        String fileNameTest = imageId + "-" + timeStamp + "-" + randomId;

        imageId++;

        Path uploadPath = Paths.get(uploadDir);
        try {
            Files.createDirectories(uploadPath);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Unable to create the directory to store uploaded files.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:8080/images/" + fileNameTest;
        } catch (Exception e) {
            throw new SomethingWentWrongException("Unable to save the uploaded file: " + fileName);
        }
    }

    public static String checkMediaType(MultipartFile file) {
        String mediaType;
        try {
            mediaType = new Tika().detect(file.getInputStream());
        } catch (IOException e) {
            throw new SomethingWentWrongException("Cannot determine the media type for the uploaded file.");
        }
        if (mediaType.equals("image/jpeg")) mediaType = ".jpeg";
        else if (mediaType.equals("image/png")) mediaType = ".png";
        else throw new UnsupportedFileTypeException();
        return mediaType;
    }
}

