package org.autoflex.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterRawMaterialsDto(
    @NotBlank(message = "Nome é obrigatório")
    String name,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Quantidade deve ser maior que zero")
    Integer quantity
) {
}