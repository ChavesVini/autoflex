package org.autoflex.service;

import java.util.List;

import org.autoflex.entity.RawMaterialsEntity;
import org.autoflex.entity.dto.RegisterRawMaterialsDto;
import org.autoflex.repository.RawMaterialsRepository;

import io.quarkus.panache.common.Page;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RawMaterialsService {
    private final RawMaterialsRepository rawMaterialsRepository;
    
    @Inject
    public RawMaterialsService(RawMaterialsRepository rawMaterialsRepository) {
        this.rawMaterialsRepository = rawMaterialsRepository;
    }
    
    @Transactional
    public RegisterRawMaterialsDto registerRawMaterial(RegisterRawMaterialsDto request) {

        var product = new RawMaterialsEntity();
        product.setName(request.name());
        product.setQuantity(request.quantity());

        rawMaterialsRepository.persist(product);

        return new RegisterRawMaterialsDto(product.getName(), product.getQuantity());
    }

    public RegisterRawMaterialsDto getRawMaterial(Long code) {

        var searchProduct = rawMaterialsRepository.findById(code);

        if (searchProduct == null) {
            return null;
        }

        return new RegisterRawMaterialsDto(searchProduct.getName(), searchProduct.getQuantity());
    }

    public List<RegisterRawMaterialsDto> getAllRawMaterials(int page, int size) {
        return rawMaterialsRepository.findAll()
            .page(Page.of(page, size))
            .stream()
            .map(entity -> new RegisterRawMaterialsDto(
                entity.getName(),
                entity.getQuantity()
            ))
            .toList();
    }
}
