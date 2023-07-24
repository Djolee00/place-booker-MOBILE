package com.placebooker.domain;

import com.placebooker.constraint.DateOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@DateOrder(
    dateFromField = "bookedFrom",
    dateToField = "bookedTo",
    message = "Booked from date must be less or equal to booked to date")
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
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column(name = "booked_from")
  private LocalDate bookedFrom;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column(name = "booked_to")
  private LocalDate bookedTo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;
}
