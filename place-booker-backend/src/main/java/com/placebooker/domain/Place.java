package com.placebooker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "place")
public class Place {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "image_url")
  private String imageUrl;

  @NotNull
  @Column(name = "price")
  private Double price;

  @NotNull
  @FutureOrPresent
  @Column(name = "available_from")
  private LocalDate availableFrom;

  @NotNull
  @FutureOrPresent
  @Column(name = "available_to")
  private LocalDate availableTo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "place_location_id")
  private PlaceLocation placeLocation;
}
