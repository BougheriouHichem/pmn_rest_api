package com.example.pmnapirest.Entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

    @Id
    private Long id;

    @NotBlank
    private String title;
    private String description;
    private boolean completed;

    // Getter and Setter methods

}

