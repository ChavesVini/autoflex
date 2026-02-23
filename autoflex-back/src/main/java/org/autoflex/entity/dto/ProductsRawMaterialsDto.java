package org.autoflex.entity.dto;

public record ProductsRawMaterialsDto(
    String productId,
    String rawMaterialsId,
    Integer quantity
) {
}
