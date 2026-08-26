package com.elitetech_inc.ensarkbank.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
public class Utils {

    @Value("${image.upload.dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            Path dir = Paths.get(uploadDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create upload directory: " + uploadDir, e);
        }
    }

    public String generateReference() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid.substring(0, 12);
    }

    /**
     * Best-effort client IP for the in-flight HTTP request, used by fraud
     * detection (see FraudDetectionService). Returns null when called
     * outside a request (schedulers, background jobs).
     */
    public String getClientIp() {
        HttpServletRequest request = currentRequest();
        if (request == null) return null;

        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    /**
     * Best-effort device/user-agent string for the in-flight HTTP request,
     * used by fraud detection. Returns null outside a request.
     */
    public String getDeviceInfo() {
        HttpServletRequest request = currentRequest();
        return request == null ? null : request.getHeader("User-Agent");
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs == null ? null : attrs.getRequest();
    }

    public String generateRouteNumber(){
        final String fixedRoute = "6830";
        String randomPart = String.format("%05d",
                new Random().nextInt(100000));
        String accNumber =fixedRoute + randomPart;
        return accNumber;
    }


    /**
     * @param file      — uploaded MultipartFile
     * @param subFolder — "customer","kyc", "employee"
     * @param prefix    —  name, docType
     * @return stored filename
     */
    public String uploadFile(MultipartFile file, String subFolder, String prefix) {
        try {
            Path dir = Paths.get(uploadDir, subFolder);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf("."));
            }

            String fileName = prefix.trim().replaceAll("\\s+", "_")
                    + "_" + UUID.randomUUID() + ext;

            Files.copy(file.getInputStream(), dir.resolve(fileName));
            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("File upload failed [" + prefix + "]: " + e.getMessage());
        }
    }

    public void deleteFile(String subFolder, String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, subFolder, fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

        } catch (Exception e) {
            throw new RuntimeException("File delete failed [" + fileName + "]: " + e.getMessage());
        }
    }
}
