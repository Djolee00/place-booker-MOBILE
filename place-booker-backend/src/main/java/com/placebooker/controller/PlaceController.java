package com.placebooker.controller;

import com.placebooker.domain.Place;
import com.placebooker.dto.PlaceDto;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.mapper.PlaceMapper;
import com.placebooker.service.PlaceService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

  private final PlaceService placeService;

  public PlaceController(PlaceService placeService) {
    this.placeService = placeService;
  }

  @GetMapping
  public ResponseEntity<List<PlaceDto>> getPlaces(Pageable pageable) {
    List<Place> places = placeService.getPlaces(pageable);
    return ResponseEntity.ok(places.stream().map(PlaceMapper::toDto).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlaceDto> getPlace(@PathVariable Long id) {
    Place place = placeService.getPlaceById(id);
    if (place == null) {
      throw new NotFoundException("Place with id: " + id + " can not be found");
    }
    return ResponseEntity.ok(PlaceMapper.toDto(place));
  }
}
