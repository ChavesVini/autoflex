package org.autoflex.service;

import org.autoflex.entity.RawMaterialsEntity;
import org.autoflex.entity.dto.RegisterRawMaterialsDto;
import org.autoflex.repository.RawMaterialsRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class ProductsRawMaterialsService {
    private final RawMaterialsRepository rawMaterialsRepository;
    
    @Inject
    public ProductsRawMaterialsService(RawMaterialsRepository rawMaterialsRepository) {
        this.rawMaterialsRepository = rawMaterialsRepository;
    }
    
    @Transactional
    public RegisterRawMaterialsDto registerProduct(RegisterRawMaterialsDto request) {

        var product = new RawMaterialsEntity();
        product.setName(request.name());
        product.setQuantity(request.quantity());

        rawMaterialsRepository.persist(product);

        return new RegisterRawMaterialsDto(product.getName(), product.getQuantity());
    }

    public void getProduct(String code) {
        System.out.println("Getting product with code: " + code);
    }

    public void updateProduct(String code, String name, double price) {
        System.out.println("Product updated: " + code + ", " + name + ", " + price);
    }
}
