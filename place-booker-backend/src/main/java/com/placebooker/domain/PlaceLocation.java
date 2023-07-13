package com.placebooker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "place_location")
public class PlaceLocation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "address")
  private String address;

  @NotNull
  @Column(name = "static_map_image_url")
  private String staticMapImageUrl;
}
