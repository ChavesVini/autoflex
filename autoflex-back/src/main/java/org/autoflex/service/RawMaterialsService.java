package org.autoflex.service;

import java.util.List;

import org.autoflex.entity.RawMaterialsEntity;
import org.autoflex.entity.dto.PageResponseDto;
import org.autoflex.entity.dto.RawMaterialsDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.repository.RawMaterialsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class RawMaterialsService {
    private final RawMaterialsRepository rawMaterialsRepository;
    
    @Inject
    public RawMaterialsService(RawMaterialsRepository rawMaterialsRepository) {
        this.rawMaterialsRepository = rawMaterialsRepository;
    }
    
    @Transactional
    public RawMaterialsDto registerRawMaterial(RawMaterialsDto request) {

        var product = new RawMaterialsEntity();
        product.setName(request.name());
        product.setQuantity(request.quantity());

        rawMaterialsRepository.persist(product);

        return new RawMaterialsDto(product.getName(), product.getQuantity());
    }

    public RawMaterialsEntity getRawMaterial(Long code) {

        var rawMaterial = rawMaterialsRepository.findById(code);

        if (rawMaterial == null) {
            throw new NoSuchElementException("O material não existe!");
        }

        return rawMaterial;
    }

    public PageResponseDto<RawMaterialsEntity> getAllRawMaterials(Integer page, Integer size) {

        var query = rawMaterialsRepository.findAll(Sort.by("id"));

        Long total = query.count();

        List<RawMaterialsEntity> products = query
                .page(Page.of(page, size))
                .list();

        Integer totalPages = (int) Math.ceil((double) total / size);

        return new PageResponseDto<RawMaterialsEntity>(products, total, page, size, totalPages);
    }

    @Transactional
    public RawMaterialsEntity updateRawMaterial(RawMaterialsDto update, Long code) {

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

        rawMaterialsRepository.persist(updateRawMaterial);

        return updateRawMaterial;
    }
    
    @Transactional
    public void deleteRawMaterial(Long code) {
        var rawMaterial = rawMaterialsRepository.findById(code);

        if (rawMaterial == null) {
            throw new NoSuchElementException("O material não existe!");
        }

        if (rawMaterial != null) {
            rawMaterialsRepository.delete(rawMaterial);
        }
    }
}