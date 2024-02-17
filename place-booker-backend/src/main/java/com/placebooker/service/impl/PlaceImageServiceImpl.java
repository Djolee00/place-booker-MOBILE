package com.placebooker.service.impl;

import com.placebooker.client.FileServiceClient;
import com.placebooker.domain.PlaceImage;
import com.placebooker.repository.PlaceImageRepository;
import com.placebooker.service.PlaceImageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PlaceImageServiceImpl implements PlaceImageService {

    @Value("${file.service.s3.folder.uuid}")
    private UUID folderUuid;

    private final FileServiceClient fileServiceClient;
    private final PlaceImageRepository placeImageRepository;

    @Override
    @Transactional
    public PlaceImage uploadImage(MultipartFile file) {
        UUID fileUuid = fileServiceClient.uploadFile(file, folderUuid);
        PlaceImage placeImage =
                new PlaceImage()
                        .setOriginalName(file.getOriginalFilename())
                        .setContentType(file.getContentType())
                        .setUuid(fileUuid);
        placeImageRepository.save(placeImage);
        return placeImage;
    }

    @Override
    public Resource downloadFile(PlaceImage image) {
        return fileServiceClient.downloadFile(folderUuid, image.getUuid());
    }
}
