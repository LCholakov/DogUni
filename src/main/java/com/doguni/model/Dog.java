package com.doguni.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private Breed breed;

    private String coat;
    private String eyeColor;

    @Column(length = 2000)
    private String specialConsiderations;

    private String profilePicture;

    private LocalDateTime dateOfBirth;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;
}
