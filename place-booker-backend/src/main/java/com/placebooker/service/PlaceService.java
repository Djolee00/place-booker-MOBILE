package com.placebooker.service;

import com.placebooker.domain.Place;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceService {

    List<Place> getPlaces(Pageable pageable);

    Place getPlaceById(Long id);

    Place saveOrUpdate(Place place, @NotNull MultipartFile file);

    Place saveOrUpdate(Place place);
}
