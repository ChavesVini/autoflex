package org.autoflex.entity.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductsResponseDto(
    @NotNull(message = "Id é obrigatório")
    Long id,

    @NotBlank(message = "Nome é obrigatório")
    String name,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    BigDecimal price
) {}