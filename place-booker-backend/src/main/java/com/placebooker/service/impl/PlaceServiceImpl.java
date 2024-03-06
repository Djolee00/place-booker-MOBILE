package com.placebooker.service.impl;

import com.placebooker.domain.Place;
import com.placebooker.domain.PlaceImage;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.PlaceRepository;
import com.placebooker.service.PlaceImageService;
import com.placebooker.service.PlaceService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceImageService placeImageService;

    @Override
    @Transactional(readOnly = true)
    public List<Place> getPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Place getPlaceById(Long id) {
        return placeRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Place with ID: " + id + " can't be found"));
    }

    @Override
    @Transactional
    public Place saveOrUpdate(Place place, @NotNull MultipartFile file) {
        PlaceImage placeImage = placeImageService.uploadImage(file);
        place.setPlaceImage(placeImage);
        return placeRepository.save(place);
    }

    @Override
    @Transactional
    public Place saveOrUpdate(Place place) {
        return placeRepository.save(place);
    }
}
