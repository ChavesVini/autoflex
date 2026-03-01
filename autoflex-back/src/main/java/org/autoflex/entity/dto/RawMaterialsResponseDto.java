package org.autoflex.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialsResponseDto(
    @NotNull(message = "Id é obrigatório")
    Long id,

    @NotBlank(message = "Nome é obrigatório")
    String name,

    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero(message = "Quantidade deve ser maior que -1")
    Integer quantity
) {
}