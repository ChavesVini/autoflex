package org.autoflex.entity.dto;

public record ProductProductionDto(
    Long id,
    String name,
    boolean canProduce,
    int quantityPossible
) {}