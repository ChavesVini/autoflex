package org.autoflex.service;

import org.autoflex.entity.ProductsEntity;
import org.autoflex.entity.dto.RegisterProductsDto;
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
    public RegisterProductsDto registerProduct(RegisterProductsDto request) {

        var product = new ProductsEntity();
        product.setName(request.name());
        product.setPrice(request.price());

        productRepository.persist(product);

        return new RegisterProductsDto(product.getName(), product.getPrice());
    }

    public void getProduct(String code) {
        System.out.println("Getting product with code: " + code);
    }

    public void updateProduct(String code, String name, double price) {
        System.out.println("Product updated: " + code + ", " + name + ", " + price);
    }
}