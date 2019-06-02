package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.controller.exception.BadRequestException;
import io.github.lukamaletin.ftnscholar.controller.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage-location}")
    private String storagePath;

    private static final String FILE_EXTENSION = "pdf";

    @Override
    public String store(MultipartFile file, String id) {
        final String fileName = String.format("%s.%s", id, FILE_EXTENSION);
        final String filePath = String.format("%s/%s", storagePath, fileName);
        final File newFile = new File(filePath);

        try {
            if (newFile.exists()) {
                Files.delete(newFile.toPath());
            }
            Files.createFile(newFile.toPath());
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file");
        }

        try (final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
            final byte[] bytes = file.getBytes();
            stream.write(bytes);
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file");
        }

        return fileName;
    }

    @Override
    public byte[] load(String fileName) {
        try {
            final File file = new File(String.format("%s/%s.%s", storagePath, fileName, FILE_EXTENSION));
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new NotFoundException("File not found");
        }
    }
}
