package com.placebooker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "booking")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "title")
  private String title;

  @NotNull
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @Positive
  @NotNull
  @Column(name = "guest_number")
  private Integer guestNumber;

  @NotNull
  @FutureOrPresent
  @Column(name = "booked_from")
  private LocalDate bookedFrom;

  @NotNull
  @FutureOrPresent
  @Column(name = "booked_to")
  private LocalDate bookedTo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;
}
