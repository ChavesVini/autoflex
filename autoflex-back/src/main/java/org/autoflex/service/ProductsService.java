package org.autoflex.service;

import java.util.Set;

import org.autoflex.entity.ProductsEntity;
import org.autoflex.entity.dto.ProductsDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.repository.ProductsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductsService {
    private final ProductsRepository productRepository;
    
    @Inject
    public ProductsService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Transactional
    public ProductsDto registerProduct(ProductsDto request) {

        var product = new ProductsEntity();
        product.setName(request.name());
        product.setPrice(request.price());

        productRepository.persist(product);

        return new ProductsDto(product.getName(), product.getPrice());
    }

    public ProductsEntity getProduct(Long code) {

        var product = productRepository.findById(code);

        if (product == null) {
            throw new NoSuchElementException("O produto não existe!");
        }

        return product;
    }

    public Set<ProductsEntity> getAllProducts(int page, int size) {
        return productRepository.findAll().stream()
                .skip((long) page * size)
                .limit(size)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Transactional        
    public ProductsEntity updateProduct(ProductsDto update, Long code) { 
        var updateProduct = productRepository.findById(code);

        if (updateProduct == null) {
            throw new NoSuchElementException("O produto não existe!");
        }

        boolean hasUpdates = false;

        if (update.name() != null) {
            updateProduct.setName(update.name());
            hasUpdates = true;
        } 
        
        if (update.price() != null) {
            updateProduct.setPrice(update.price());
            hasUpdates = true;
        } 
        
        if (!hasUpdates) {
            throw new EmptyUpdateRequestException("Nenhum campo para atualizar foi fornecido.");
        }

        productRepository.persist(updateProduct);

        return updateProduct;
    }

    @Transactional
    public void deleteProduct(Long code) {
        var product = productRepository.findById(code);

        if (product == null) {
            throw new NoSuchElementException("O produto não existe!");
        }

        if (product != null) {
            productRepository.delete(product);
        }
    }
}