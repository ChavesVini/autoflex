package org.autoflex.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductsRawMaterialsDto(
    @NotNull(message = "ProductId é obrigatório")
    Long productId,

    @NotNull(message = "RawMaterialId é obrigatório")
    Long rawMaterialId,

    @NotNull(message = "Quantidade é obrigatório")
    @PositiveOrZero(message = "Quantidade deve ser maior que -1")
    Integer quantity
) {
}
