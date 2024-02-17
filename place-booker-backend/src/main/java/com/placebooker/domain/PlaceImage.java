package com.placebooker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Accessors(chain = true)
@Table(name = "place_image")
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "uuid")
    private UUID uuid;

    @NotBlank
    @Column(name = "original_name")
    private String originalName;

    @NotBlank
    @Column(name = "content_type")
    private String contentType;

    @PrePersist
    public void prePersist() {
        if (getUuid() == null) {
            synchronized (this) {
                if (getUuid() == null) {
                    uuid = UUID.randomUUID();
                }
            }
        }
    }
}
