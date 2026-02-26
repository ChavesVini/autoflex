package org.autoflex.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductsRawMaterialsDto(
    @NotNull(message = "ProductId é obrigatório")
    Integer productId,

    @NotNull(message = "RawMaterialId é obrigatório")
    Integer rawMaterialId,

    @NotNull(message = "Quantidade é obrigatório")
    @Positive(message = "Quantidade deve ser maior que zero")
    Integer quantity
) {
}
