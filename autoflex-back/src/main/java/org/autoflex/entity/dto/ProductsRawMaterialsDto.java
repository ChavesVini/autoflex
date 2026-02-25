package org.autoflex.entity.dto;

import jakarta.validation.constraints.NotNull;

public record ProductsRawMaterialsDto(
    @NotNull(message = "ProductId é obrigatório")
    Integer productId,

    @NotNull(message = "RawMaterialsId é obrigatório")
    String rawMaterialsId,

    @NotNull(message = "Quantidade é obrigatório")
    Integer quantity
) {
}
