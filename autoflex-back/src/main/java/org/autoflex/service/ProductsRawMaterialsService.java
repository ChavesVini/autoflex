package org.autoflex.service;

import java.util.Set;

import org.autoflex.entity.ProductsRawMaterialsEntity;
import org.autoflex.entity.dto.ProductsRawMaterialsDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.repository.ProductsRawMaterialsRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class ProductsRawMaterialsService {
    private final ProductsRawMaterialsRepository productsRawMaterialsRepository;
    
    @Inject
    public ProductsRawMaterialsService(ProductsRawMaterialsRepository productsRawMaterialsRepository) {
        this.productsRawMaterialsRepository = productsRawMaterialsRepository;
    }
    
    @Transactional
    public ProductsRawMaterialsDto registerProductAndRawMaterial(ProductsRawMaterialsDto request) {

        var productAndRawMaterial = new ProductsRawMaterialsEntity();
        productAndRawMaterial.setProductId(request.productId());
        productAndRawMaterial.setRawMaterialId(request.rawMaterialId());
        productAndRawMaterial.setQuantity(request.quantity());

        productsRawMaterialsRepository.persist(productAndRawMaterial);

        return new ProductsRawMaterialsDto(
            productAndRawMaterial.getProductId(),
            productAndRawMaterial.getRawMaterialId(),
            productAndRawMaterial.getQuantity()
        );
    }

    public ProductsRawMaterialsEntity getProduct(Long code) {

        var productAndRawMaterial = productsRawMaterialsRepository.findById(code);

        if (productAndRawMaterial == null) {
            throw new NoSuchElementException("O produto e matéria-prima não existe!");
        }

        return productAndRawMaterial;
    }

    public Set<ProductsRawMaterialsEntity> getAllProducts(int page, int size) {
        return productsRawMaterialsRepository.findAll().stream()
                .skip((long) page * size)
                .limit(size)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Transactional        
    public ProductsRawMaterialsEntity updateProduct(ProductsRawMaterialsDto update, Long code) {   
        var updateProduct = productsRawMaterialsRepository.findById(code);

        if (updateProduct == null) {
            throw new NoSuchElementException("O produto e matéria-prima não existe!");
        }

        boolean hasUpdates = false;

        if (update.productId() != null) {
            updateProduct.setProductId(update.productId());
            hasUpdates = true;
        } 
        
        if (update.rawMaterialId() != null) {
            updateProduct.setRawMaterialId(update.rawMaterialId());
            hasUpdates = true;
        } 
        
        if (update.quantity() != null) {
            updateProduct.setQuantity(update.quantity());
            hasUpdates = true;
        } 
        
        if (!hasUpdates) {
            throw new EmptyUpdateRequestException("Nenhum campo para atualizar foi fornecido.");
        }

        productsRawMaterialsRepository.persist(updateProduct);

        return updateProduct;
    }

    @Transactional
    public void deleteProduct(Long code) {
        var product = productsRawMaterialsRepository.findById(code);

        if (product == null) {
            throw new NoSuchElementException("O produto e matéria-prima não existe!");
        }

        if (product != null) {
            productsRawMaterialsRepository.delete(product);
        }
    }
}