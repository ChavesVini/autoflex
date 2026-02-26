package org.autoflex.entity.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PageResponseDto<T> (
    @NotEmpty(message = "Lista é obrigatória")
    List<T> content,

    @NotNull(message = "Total de elementos é obrigatório")
    Long totalElements,

    @NotNull(message = "Página é obrigatória")
    Integer page,
    
    @NotNull(message = "Tamanho da página é obrigatório")
    Integer size,
    
    @NotNull(message = "Total de páginas é obrigatório")
    Integer totalPages
) {}
