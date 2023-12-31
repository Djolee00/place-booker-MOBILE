package com.placebooker.service.impl;

import com.placebooker.domain.Place;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.PlaceRepository;
import com.placebooker.service.PlaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

  private final PlaceRepository placeRepository;

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
        .orElseThrow(() -> new NotFoundException("Place with ID: " + id + " can't be found"));
  }

  @Override
  @Transactional
  public Place saveOrUpdate(Place place) {
    return placeRepository.save(place);
  }
}
