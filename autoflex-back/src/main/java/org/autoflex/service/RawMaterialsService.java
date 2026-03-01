package org.autoflex.service;

import java.util.List;

import org.autoflex.entity.RawMaterialsEntity;
import org.autoflex.entity.dto.PageResponseDto;
import org.autoflex.entity.dto.RawMaterialsResponseDto;
import org.autoflex.entity.dto.RawMaterialsUpdateDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.repository.RawMaterialsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class RawMaterialsService {
    private final RawMaterialsRepository rawMaterialsRepository;
    private final ProductsRawMaterialsService productsRawMaterialsService;
    private PanacheQuery<RawMaterialsEntity> query;
    
    @Inject
    public RawMaterialsService(RawMaterialsRepository rawMaterialsRepository, ProductsRawMaterialsService productsRawMaterialsService) {
        this.rawMaterialsRepository = rawMaterialsRepository;
        this.productsRawMaterialsService = productsRawMaterialsService;
    }
    
    @Transactional
    public RawMaterialsResponseDto registerRawMaterial(RawMaterialsUpdateDto request) {

        var product = new RawMaterialsEntity();
        product.setName(request.name());
        product.setQuantity(request.quantity());

        rawMaterialsRepository.persist(product);

        return new RawMaterialsResponseDto(product.id, product.getName(), product.getQuantity());
    }

    public RawMaterialsEntity getRawMaterial(Long code) {

        var rawMaterial = rawMaterialsRepository.findById(code);

        if (rawMaterial == null) {
            throw new NoSuchElementException("O material não existe!");
        }

        return rawMaterial;
    }

    public PageResponseDto<RawMaterialsEntity> getAllRawMaterials(String name, Integer page, Integer size) {

        String formatted = name == null ? "" : name.trim();

        if (formatted == null || formatted.isBlank()) {
            query = rawMaterialsRepository.findAll(Sort.by("id"));
        } else {
            query = rawMaterialsRepository.find(
                    "LOWER(name) LIKE LOWER(?1)",
                    Sort.by("id"),
                    "%" + formatted + "%"
            );
        }

        Long total = query.count();

        List<RawMaterialsEntity> rawMaterials = query
                .page(Page.of(page, size))
                .list();

        Integer totalPages = (int) Math.ceil((double) total / size);

        return new PageResponseDto<>(
                rawMaterials,
                total,
                page,
                size,
                totalPages
        );
    }

    @Transactional
    public RawMaterialsResponseDto updateRawMaterial(RawMaterialsUpdateDto update, Long code) {

        var updateRawMaterial = rawMaterialsRepository.findById(code);

        if (updateRawMaterial == null) {
            throw new NoSuchElementException("O material não existe!");
        }

        boolean hasUpdates = false;

        if (update.name() != null) {
            updateRawMaterial.setName(update.name());
            hasUpdates = true;
        } 
        
        if (update.quantity() != null) {
            updateRawMaterial.setQuantity(update.quantity());
            hasUpdates = true;
        }

        if (!hasUpdates) {
            throw new EmptyUpdateRequestException("Nenhum campo para atualizar foi fornecido.");
        }

        return new RawMaterialsResponseDto(
            code,
            updateRawMaterial.getName(),
            updateRawMaterial.getQuantity()
        );
    }
    
    @Transactional
    public void deleteRawMaterial(Long code) {
        var rawMaterial = rawMaterialsRepository.findById(code);
        
        productsRawMaterialsService.validateRawMaterialIsNotAssociated(code);
        
        if (rawMaterial == null) {
            throw new NoSuchElementException("O material não existe!");
        }

        if (rawMaterial != null) {
            rawMaterialsRepository.delete(rawMaterial);
        }
    }
}