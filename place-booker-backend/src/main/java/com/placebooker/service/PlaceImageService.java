package com.placebooker.service;

import com.placebooker.domain.PlaceImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
public interface PlaceImageService {

    PlaceImage uploadImage(@NotNull MultipartFile file);

    Resource downloadFile(@NotNull PlaceImage image);
}
