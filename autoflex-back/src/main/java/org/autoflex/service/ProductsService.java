package org.autoflex.service;

import java.util.List;

import org.autoflex.entity.ProductsEntity;
import org.autoflex.entity.dto.PageResponseDto;
import org.autoflex.entity.dto.ProductsDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.repository.ProductsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

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

    public PageResponseDto<ProductsEntity> getAllProducts(Integer page, Integer size) {

        var query = productRepository.findAll(Sort.by("id"));

        Long total = query.count();

        List<ProductsEntity> products = query
                .page(Page.of(page, size))
                .list();

        Integer totalPages = (int) Math.ceil((double) total / size);

        return new PageResponseDto<ProductsEntity>(products, total, page, size, totalPages);
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