package org.autoflex.service;

import java.util.List;

import org.autoflex.entity.ProductsEntity;
import org.autoflex.entity.dto.RegisterProductsDto;
import org.autoflex.repository.ProductsRepository;

import io.quarkus.panache.common.Page;

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
    public RegisterProductsDto registerProduct(RegisterProductsDto request) {

        var product = new ProductsEntity();
        product.setName(request.name());
        product.setPrice(request.price());

        productRepository.persist(product);

        return new RegisterProductsDto(product.getName(), product.getPrice());
    }

    public RegisterProductsDto getProduct(Long code) {

        var searchProduct = productRepository.findById(code);

        if (searchProduct == null) {
            return null;
        }

        return new RegisterProductsDto(searchProduct.getName(), searchProduct.getPrice());
    }

    public List<RegisterProductsDto> getAllProducts(int page, int size) {
        return productRepository.findAll()
            .page(Page.of(page, size))
            .stream()
            .map(entity -> new RegisterProductsDto(
                entity.getName(),
                entity.getPrice()
            ))
            .toList();
    }
}