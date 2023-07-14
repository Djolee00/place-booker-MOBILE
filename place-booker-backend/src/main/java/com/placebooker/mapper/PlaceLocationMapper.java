package com.placebooker.mapper;

import com.placebooker.domain.PlaceLocation;
import com.placebooker.dto.PlaceLocationDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceLocationMapper {
  public static PlaceLocation toEntity(PlaceLocationDto placeLocationDto) {
    return PlaceLocation.builder()
        .address(placeLocationDto.address())
        .staticMapImageUrl(placeLocationDto.staticMapImageUrl())
        .build();
  }

  public static PlaceLocationDto toDto(PlaceLocation placeLocation) {
    return PlaceLocationDto.builder()
        .address(placeLocation.getAddress())
        .staticMapImageUrl(placeLocation.getStaticMapImageUrl())
        .build();
  }
}
