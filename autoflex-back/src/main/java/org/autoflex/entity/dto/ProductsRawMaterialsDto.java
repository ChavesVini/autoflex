package org.autoflex.entity.dto;

public record ProductsRawMaterialsDto(
    Integer productId,
    String rawMaterialsId,
    Integer quantity
) {
}
