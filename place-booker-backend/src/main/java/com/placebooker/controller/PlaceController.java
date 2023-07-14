package com.placebooker.controller;

import com.placebooker.domain.Place;
import com.placebooker.domain.PlaceLocation;
import com.placebooker.domain.User;
import com.placebooker.dto.PlaceDto;
import com.placebooker.mapper.PlaceLocationMapper;
import com.placebooker.mapper.PlaceMapper;
import com.placebooker.service.PlaceService;
import com.placebooker.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

  private final PlaceService placeService;
  private final UserService userService;

  public PlaceController(PlaceService placeService, UserService userService) {
    this.placeService = placeService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<PlaceDto>> getPlaces(Pageable pageable) {
    List<Place> places = placeService.getPlaces(pageable);
    return ResponseEntity.ok(places.stream().map(PlaceMapper::toDto).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlaceDto> getPlace(@PathVariable Long id) {
    Place place = placeService.getPlaceById(id);
    return ResponseEntity.ok(PlaceMapper.toDto(place));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Long> savePlace(@Valid @RequestBody PlaceDto placeDto) {
    User user = userService.getUserById(placeDto.user().id());
    PlaceLocation placeLocation = PlaceLocationMapper.toEntity(placeDto.placeLocation());

    Place place = PlaceMapper.toEntity(placeDto);
    place.setUser(user);
    place.setPlaceLocation(placeLocation);

    return ResponseEntity.ok(placeService.saveOrUpdate(place));
  }
}
