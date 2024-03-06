package com.placebooker.controller;

import com.placebooker.domain.Place;
import com.placebooker.domain.PlaceLocation;
import com.placebooker.domain.User;
import com.placebooker.dto.PlaceDto;
import com.placebooker.mapper.PlaceLocationMapper;
import com.placebooker.mapper.PlaceMapper;
import com.placebooker.service.PlaceImageService;
import com.placebooker.service.PlaceService;
import com.placebooker.service.UserService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;
    private final UserService userService;
    private final PlaceImageService placeImageService;

    public PlaceController(
            PlaceService placeService,
            UserService userService,
            PlaceImageService placeImageService) {
        this.placeService = placeService;
        this.userService = userService;
        this.placeImageService = placeImageService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaces(Pageable pageable) {
        List<Place> places = placeService.getPlaces(pageable);
        return ResponseEntity.ok(
                places.stream().map(PlaceMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getPlace(@PathVariable Long id) {
        Place place = placeService.getPlaceById(id);
        Resource placeImage;
        PlaceDto placeDto;
        if (place.getPlaceImage() != null) {
            placeImage = placeImageService.downloadFile(place.getPlaceImage());
            try {
                placeDto =
                        PlaceMapper.toDto(
                                place,
                                Base64.getEncoder()
                                        .encodeToString(placeImage.getContentAsByteArray()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            placeDto = PlaceMapper.toDto(place);
        }

        return ResponseEntity.ok(placeDto);
    }

    //    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    //    public ResponseEntity<Long> createPlace(
    //            @Valid @RequestPart("place") PlaceDto placeDto,
    //            @NotNull @RequestPart("image") MultipartFile file) {
    //
    //        User user = userService.getUserById(placeDto.user().id());
    //        PlaceLocation placeLocation = PlaceLocationMapper.toEntity(placeDto.placeLocation());
    //
    //        Place place = PlaceMapper.toEntity(placeDto);
    //        place.setUser(user);
    //        place.setPlaceLocation(placeLocation);
    //
    //        return new ResponseEntity<>(
    //                placeService.saveOrUpdate(place, file).getId(), HttpStatus.CREATED);
    //    }

    @PostMapping
    public ResponseEntity<Long> createPlace(@RequestBody PlaceDto placeDto) {

        User user = userService.getUserById(placeDto.user().id());
        PlaceLocation placeLocation = PlaceLocationMapper.toEntity(placeDto.placeLocation());

        Place place = PlaceMapper.toEntity(placeDto);
        place.setUser(user);
        place.setPlaceLocation(placeLocation);

        return new ResponseEntity<>(placeService.saveOrUpdate(place).getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDto> updatePlace(
            @PathVariable Long id, @Valid @RequestBody PlaceDto placeDto) {
        Place place = placeService.getPlaceById(id);

        place.setTitle(placeDto.title())
                .setDescription(placeDto.description())
                .setPrice(placeDto.price())
                .setAvailableFrom(placeDto.availableFrom())
                .setAvailableTo(placeDto.availableTo());

        return ResponseEntity.ok(PlaceMapper.toDto(placeService.saveOrUpdate(place)));
    }
}
