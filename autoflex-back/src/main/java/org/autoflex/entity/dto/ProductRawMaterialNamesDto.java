package org.autoflex.entity.dto;

public record ProductRawMaterialNamesDto(
    Long id,
    Long productId,
    Long rawMaterialId,
    String rawMaterialName,
    Integer quantity
) {}