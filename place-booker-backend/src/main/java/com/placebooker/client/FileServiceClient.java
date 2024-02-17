package com.placebooker.client;

import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceClient {
    UUID uploadFile(MultipartFile file, UUID folderUuid);

    Resource downloadFile(UUID folderUuid, UUID fileUuid);

    void updateFile(MultipartFile file, UUID folderUuid, UUID fileUuid);

    void deleteFile(UUID folderUuid, UUID fileUuid, String fileName);
}
