package com.placebooker.mapper;

import com.placebooker.domain.Place;
import com.placebooker.dto.PlaceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceMapper {

  public static Place toEntity(PlaceDto placeDto) {
    return Place.builder()
        .title(placeDto.title())
        .description(placeDto.description())
        .imageUrl(placeDto.imageUrl())
        .price(placeDto.price())
        .availableFrom(placeDto.availableFrom())
        .availableTo(placeDto.availableTo())
        .build();
  }

  public static PlaceDto toDto(Place place) {
    return PlaceDto.builder()
        .id(place.getId())
        .title(place.getTitle())
        .description(place.getDescription())
        .imageUrl(place.getImageUrl())
        .availableFrom(place.getAvailableFrom())
        .availableTo(place.getAvailableTo())
        .price(place.getPrice())
        .user(place.getUser() != null ? UserMapper.toDto(place.getUser()) : null)
        .build();
  }
}
