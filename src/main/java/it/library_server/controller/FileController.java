package it.library_server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/files")
public class FileController {

        @Value("${file.upload-pdf-dir}")
        private String uploadPdfDir;

        @Value("${file.upload-image-dir}")
        private String uploadImageDir;

        @Value("${file.max-size:104857600}") // Default 100MB
        private long maxFileSize;

        private static final String PDF_TYPE = "application/pdf";
        private static final String[] IMAGE_TYPES = {"image/jpeg", "image/png", "image/jpg"};

        /**
         * Fayl yuklash (PDF yoki Rasm)
         */
        @PostMapping("/upload")
        public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
            String fileType = file.getContentType();
            String uploadDir;

            if (PDF_TYPE.equals(fileType)) {
                uploadDir = uploadPdfDir; // PDF uchun papka
            } else if (isImageType(fileType)) {
                uploadDir = uploadImageDir; // Rasm uchun papka
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Faqat PDF yoki rasm yuklash mumkin.");
            }

            if (file.getSize() > maxFileSize) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Fayl hajmi juda katta. Maksimal hajm " + (maxFileSize / 1024 / 1024) + "MB.");
            }

            String safeFileName = generateSafeFileName(file.getOriginalFilename());
            Path filePath = Paths.get(uploadDir).resolve(safeFileName);

            try {
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Files.write(filePath, file.getBytes());
                return ResponseEntity.ok(safeFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Fayl yuklashda xatolik yuz berdi.");
            }
        }

        /**
         * Faylni olish (PDF yoki rasm)
         */
        @GetMapping("/{fileName}")
        public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
            try {
                Path filePath = findFilePath(fileName);
                if (filePath == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }

                Resource resource = new UrlResource(filePath.toUri());
                if (!resource.exists() || !resource.isReadable()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }

                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        /**
         * Faylni o'chirish (PDF yoki rasm)
         */
        @DeleteMapping("/{fileName}")
        public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
            try {
                Path filePath = findFilePath(fileName);
                if (filePath == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fayl topilmadi.");
                }

                File file = filePath.toFile();
                if (file.exists() && file.delete()) {
                    return ResponseEntity.ok("Fayl muvaffaqiyatli o‘chirildi.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fayl topilmadi yoki o‘chirib bo‘lmadi.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Faylni o‘chirishda xatolik yuz berdi.");
            }
        }

        /**
         * Fayl nomini xavfsiz yaratish (original nomni tozalash)
         */
        private String generateSafeFileName(String originalFileName) {
            String safeName = Paths.get(originalFileName).getFileName().toString()
                    .replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            return UUID.randomUUID().toString() + "_" + safeName;
        }

        /**
         * Fayl turi rasm ekanligini tekshirish
         */
        private boolean isImageType(String fileType) {
            for (String type : IMAGE_TYPES) {
                if (type.equals(fileType)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Faylni qaysi katalogda joylashganligini aniqlash
         */
        private Path findFilePath(String fileName) {
            Path pdfPath = Paths.get(uploadPdfDir).resolve(fileName);
            Path imagePath = Paths.get(uploadImageDir).resolve(fileName);

            if (Files.exists(pdfPath)) {
                return pdfPath;
            } else if (Files.exists(imagePath)) {
                return imagePath;
            }
            return null;
        }
}
