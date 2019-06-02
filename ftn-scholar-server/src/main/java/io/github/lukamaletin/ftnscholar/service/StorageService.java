package io.github.lukamaletin.ftnscholar.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile file, String id);

    byte[] load(String id);
}
