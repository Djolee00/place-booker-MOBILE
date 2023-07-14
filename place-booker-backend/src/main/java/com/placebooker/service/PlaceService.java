package com.placebooker.service;

import com.placebooker.domain.Place;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceService {

  List<Place> getPlaces(Pageable pageable);

  Place getPlaceById(Long id);
}
