package org.autoflex.service;

import java.util.List;

import org.autoflex.entity.ProductsEntity;
import org.autoflex.entity.dto.PageResponseDto;
import org.autoflex.entity.dto.ProductsResponseDto;
import org.autoflex.entity.dto.ProductsUpdateDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.repository.ProductsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class ProductsService {
    private final ProductsRepository productRepository;
    private PanacheQuery<ProductsEntity> query;

    @Inject
    public ProductsService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Transactional
    public ProductsResponseDto registerProduct(ProductsUpdateDto request) {

        var product = new ProductsEntity();
        product.setName(request.name());
        product.setPrice(request.price());

        productRepository.persist(product);

        return new ProductsResponseDto(product.id, product.getName(), product.getPrice());
    }

    public ProductsEntity getProduct(Long code) {

        var product = productRepository.findById(code);

        if (product == null) {
            throw new NoSuchElementException("O produto não existe!");
        }

        return product;
    }

    public PageResponseDto<ProductsEntity> getAllProducts(
            String name,
            Integer page,
            Integer size
    ) {

        String formatted = name == null ? "" : name.trim();

        if (formatted == null || formatted.isBlank()) {
            query = productRepository.findAll(Sort.descending("price"));
        } else {
            query = productRepository.find(
                    "LOWER(name) LIKE LOWER(?1)",
                    Sort.by("price"),
                    "%" + formatted + "%"
            );
        }

        Long total = query.count();

        List<ProductsEntity> products = query
                .page(Page.of(page, size))
                .list();

        Integer totalPages = (int) Math.ceil((double) total / size);

        return new PageResponseDto<>(
                products,
                total,
                page,
                size,
                totalPages
        );
    }

    @Transactional        
    public ProductsResponseDto updateProduct(ProductsUpdateDto update, Long code) { 
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

        return new ProductsResponseDto(
            code,
            updateProduct.getName(),
            updateProduct.getPrice()
        );
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