package com.chatbot.documentservice.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FirebaseStorageService {

    private final Bucket bucket;

    public FirebaseStorageService(FirebaseApp firebaseApp) {
        this.bucket = StorageClient.getInstance(firebaseApp).bucket();
    }

    public String uploadDocument(MultipartFile file, String conversationId, String documentId) {
        try {
            // Generate unique file name
            String fileName = generateFileName(file.getOriginalFilename(), documentId);
            String storagePath = String.format("conversations/%s/documents/%s", conversationId, fileName);

            // Upload to Firebase Storage
            Blob blob = bucket.create(storagePath, file.getBytes(), file.getContentType());

            log.info("Document uploaded successfully to Firebase: {}", storagePath);
            return storagePath;

        } catch (IOException e) {
            log.error("Failed to upload document to Firebase", e);
            throw new RuntimeException("Failed to upload document to Firebase", e);
        }
    }

    public String getDownloadUrl(String storagePath) {
        try {
            Blob blob = bucket.get(storagePath);
            if (blob != null && blob.exists()) {
                // Generate signed URL valid for 1 hour
                return blob.signUrl(1, TimeUnit.HOURS).toString();
            }
            throw new RuntimeException("Document not found: " + storagePath);
        } catch (Exception e) {
            log.error("Error generating download URL for: {}", storagePath, e);
            throw new RuntimeException("Error generating download URL", e);
        }
    }

    public void deleteDocument(String storagePath) {
        try {
            Blob blob = bucket.get(storagePath);
            if (blob != null && blob.exists()) {
                blob.delete();
                log.info("Document deleted successfully from Firebase: {}", storagePath);
            } else {
                log.warn("Document not found for deletion: {}", storagePath);
            }
        } catch (Exception e) {
            log.error("Failed to delete document from Firebase", e);
            throw new RuntimeException("Failed to delete document from Firebase", e);
        }
    }

    public byte[] downloadDocument(String storagePath) {
        try {
            Blob blob = bucket.get(storagePath);
            if (blob != null && blob.exists()) {
                return blob.getContent();
            }
            throw new RuntimeException("Document not found in Firebase Storage: " + storagePath);
        } catch (Exception e) {
            log.error("Failed to download document from Firebase", e);
            throw new RuntimeException("Failed to download document from Firebase", e);
        }
    }

    public String getBucketName() {
        return bucket.getName();
    }

    private String generateFileName(String originalFileName, String documentId) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return documentId + "_" + System.currentTimeMillis() + extension;
    }
}