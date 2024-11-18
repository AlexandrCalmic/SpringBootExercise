package com.koerber.supplychain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight cannot be negative")
    private Double weight;

    @NotNull(message = "Volume cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Volume cannot be negative")
    private Double volume;

}
