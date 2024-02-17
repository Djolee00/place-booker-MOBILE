package com.placebooker.client.impl;

import com.placebooker.client.FileServiceClient;
import com.placebooker.dto.fs.FileServiceResponse;
import com.placebooker.exception.custom.FileServiceException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class FileServiceClientImpl implements FileServiceClient {

    private final WebClient webClient;
    private static final String UPLOAD_ENDPOINT = "/upload";
    private static final String DOWNLOAD_ENDPOINT = "/download";
    private static final String DELETE_ENDPOINT = "/delete";
    private static final String REQUEST_PART_FILE = "file";
    private static final String REQUEST_PART_FOLDER_UUID = "folderUuid";
    private static final String REQUEST_PART_FILE_UUID = "fileUuid";
    private static final String REQUEST_PART_FILE_NAME = "fileName";

    public FileServiceClientImpl(@Value("${file.service.s3.base.url}") String baseUrl) {
        if (StringUtils.isBlank(baseUrl)) {
            log.error("Cannot configure FileServiceClient. Sef url is empty");
            this.webClient = null;
            return;
        }

        this.webClient =
                WebClient.builder()
                        .codecs(
                                configurer ->
                                        configurer
                                                .defaultCodecs()
                                                .maxInMemorySize(16 * 1024 * 1024))
                        .baseUrl(baseUrl)
                        .build();
    }

    @Override
    public UUID uploadFile(MultipartFile file, UUID folderUuid) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part(REQUEST_PART_FILE, file.getResource());
        builder.part(REQUEST_PART_FOLDER_UUID, folderUuid.toString());

        try {
            FileServiceResponse response =
                    webClient
                            .post()
                            .uri(UPLOAD_ENDPOINT)
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .body(BodyInserters.fromMultipartData(builder.build()))
                            .retrieve()
                            .bodyToMono(FileServiceResponse.class)
                            .block();
            if (response != null && response.isSuccessful()) {
                return UUID.fromString((String) response.getData());
            } else {
                throw new FileServiceException("File Service encountered problem");
            }
        } catch (Exception e) {
            throw new FileServiceException("File Service encountered problem", e);
        }
    }

    @Override
    public Resource downloadFile(UUID folderUuid, UUID fileUuid) {
        try {
            return webClient
                    .get()
                    .uri(
                            uriBuilder ->
                                    uriBuilder
                                            .path(DOWNLOAD_ENDPOINT)
                                            .queryParam(REQUEST_PART_FILE_UUID, fileUuid)
                                            .queryParam(REQUEST_PART_FOLDER_UUID, folderUuid)
                                            .build())
                    .retrieve()
                    .bodyToMono(Resource.class)
                    .block();
        } catch (Exception e) {
            throw new FileServiceException("File Service encountered problem", e);
        }
    }

    @Override
    public void updateFile(MultipartFile file, UUID folderUuid, UUID fileUuid) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part(REQUEST_PART_FILE, file.getResource());
        builder.part(REQUEST_PART_FOLDER_UUID, folderUuid.toString());
        builder.part(REQUEST_PART_FILE_UUID, fileUuid.toString());

        try {
            FileServiceResponse response =
                    webClient
                            .post()
                            .uri(UPLOAD_ENDPOINT)
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .body(BodyInserters.fromMultipartData(builder.build()))
                            .retrieve()
                            .bodyToMono(FileServiceResponse.class)
                            .block();
            if (response == null || !response.isSuccessful()) {
                throw new FileServiceException("File Service encountered problem");
            }
        } catch (Exception e) {
            throw new FileServiceException("File Service encountered problem", e);
        }
    }

    @Override
    public void deleteFile(UUID folderUuid, UUID fileUuid, String fileName) {
        try {
            webClient
                    .delete()
                    .uri(
                            uriBuilder ->
                                    uriBuilder
                                            .path(DELETE_ENDPOINT)
                                            .queryParam(REQUEST_PART_FOLDER_UUID, folderUuid)
                                            .queryParam(REQUEST_PART_FILE_UUID, fileUuid)
                                            .queryParam(REQUEST_PART_FILE_NAME, fileName)
                                            .build())
                    .retrieve()
                    .bodyToMono(FileServiceResponse.class)
                    .block();
        } catch (Exception e) {
            throw new FileServiceException("File Service encountered problem", e);
        }
    }
}
