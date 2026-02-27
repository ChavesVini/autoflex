package org.autoflex.service;

import java.util.List;
import java.util.Set;

import org.autoflex.entity.ProductsEntity;
import org.autoflex.entity.ProductsRawMaterialsEntity;
import org.autoflex.entity.RawMaterialsEntity;
import org.autoflex.entity.dto.PageResponseDto;
import org.autoflex.entity.dto.ProductProductionDto;
import org.autoflex.entity.dto.ProductsRawMaterialsDto;
import org.autoflex.exception.exceptions.EmptyUpdateRequestException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.exception.exceptions.RawMaterialInUseException;
import org.autoflex.repository.ProductsRawMaterialsRepository;
import org.autoflex.repository.ProductsRepository;
import org.autoflex.repository.RawMaterialsRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductsRawMaterialsService {
    private final ProductsRawMaterialsRepository productsRawMaterialsRepository;
    private final RawMaterialsRepository rawMaterialsRepository;
    private final ProductsRepository productsRepository;
    
    @Inject
    public ProductsRawMaterialsService(
        ProductsRawMaterialsRepository productsRawMaterialsRepository, 
        RawMaterialsRepository rawMaterialsRepository, 
        ProductsRepository productsRepository) 
    {
        this.productsRawMaterialsRepository = productsRawMaterialsRepository;
        this.rawMaterialsRepository = rawMaterialsRepository;
        this.productsRepository = productsRepository;
    }
    
    @Transactional
    public ProductsRawMaterialsDto registerProductAndRawMaterial(ProductsRawMaterialsDto request) {

        ProductsEntity product = productsRepository.findById(request.productId());
        
        if (product == null) {
            throw new NoSuchElementException("Produto não encontrado");
        }

        RawMaterialsEntity rawMaterial = rawMaterialsRepository.findById(request.rawMaterialId());

        if (rawMaterial == null) {
            throw new NoSuchElementException("Matéria-prima não encontrada");
        }

        ProductsRawMaterialsEntity productAndRawMaterial = new ProductsRawMaterialsEntity();
        productAndRawMaterial.setProduct(product);
        productAndRawMaterial.setRawMaterial(rawMaterial);
        productAndRawMaterial.setQuantity(request.quantity());

        productsRawMaterialsRepository.persist(productAndRawMaterial);

        return new ProductsRawMaterialsDto(
            productAndRawMaterial.getProduct().id,
            productAndRawMaterial.getRawMaterial().id,
            productAndRawMaterial.getQuantity()
        );
    }

    public ProductsRawMaterialsEntity getProductAndRawMaterial(Long code) {

        var productAndRawMaterial = productsRawMaterialsRepository.findById(code);

        if (productAndRawMaterial == null) {
            throw new NoSuchElementException("O produto e matéria-prima não existe!");
        }

        return productAndRawMaterial;
    }

    public Set<ProductsRawMaterialsEntity> getAllProductsAndRawMaterials(int page, int size) {
        return productsRawMaterialsRepository.findAll().stream()
                .skip((long) page * size)
                .limit(size)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Transactional        
    public ProductsRawMaterialsEntity updateProductAndRawMaterial(ProductsRawMaterialsDto update, Long code) {   
        var updateProduct = productsRawMaterialsRepository.findById(code);

        if (updateProduct == null) {
            throw new NoSuchElementException("O produto e matéria-prima não existe!");
        }

        boolean hasUpdates = false;

        if (update.productId() != null) {
            ProductsEntity product = productsRepository.findById(update.productId());
            if (product == null) {
                throw new NoSuchElementException("Produto não encontrado");
            }
            updateProduct.setProduct(product);
            hasUpdates = true;
        }

        if (update.rawMaterialId() != null) {
            RawMaterialsEntity material = rawMaterialsRepository
                .findById(update.rawMaterialId());
                
            if (material == null) {
                throw new NoSuchElementException("Matéria-prima não encontrada");
            }

            updateProduct.setRawMaterial(material);
            hasUpdates = true;
        }

        if (update.rawMaterialId() != null) {
            RawMaterialsEntity material = rawMaterialsRepository
                .findById(update.rawMaterialId());
                
            if (material == null) {
                throw new NoSuchElementException("Matéria-prima não encontrada");
            }

            updateProduct.setRawMaterial(material);
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
    public void deleteProductAndRawMaterial(Long code) {
        var product = productsRawMaterialsRepository.findById(code);

        if (product == null) {
            throw new NoSuchElementException("O produto e matéria-prima não existe!");
        }

        if (product != null) {
            productsRawMaterialsRepository.delete(product);
        }
    }

    public void validateRawMaterialIsNotAssociated(Long rawMaterialId) {
        boolean isAssociated = productsRawMaterialsRepository
                .count("rawMaterial.id", rawMaterialId) > 0;

        if (isAssociated) {
            throw new RawMaterialInUseException(
                    "Raw material is associated with products and cannot be deleted."
            );
        }
    }

    @Transactional
    public void validateProductIsNotAssociated(Long productId) {
        boolean isAssociated = productsRawMaterialsRepository
                .count("product.id", productId) > 0;

        if (isAssociated) {
            productsRawMaterialsRepository.delete("product.id", productId);
        }
    }
    
    @Transactional
    public PageResponseDto<ProductProductionDto> getProductsProductionPossibilities(
            String name,
            Integer page,
            Integer size
    ) {

        Sort sort = Sort.by("id");
        PanacheQuery<ProductsEntity> query;

        String formatted = name == null ? "" : name.trim();

        if (formatted == null || formatted.isBlank()) {
            query = productsRepository.findAll(sort);
        } else {
            query = productsRepository.find(
                    "LOWER(name) LIKE LOWER(?1)",
                    sort,
                    "%" + formatted + "%"
            );
        }

        Long total = query.count();

        List<ProductProductionDto> content = query
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(product -> {

                    int possible = calculatePossible(product);

                    return new ProductProductionDto(
                            product.id,
                            product.getName(),
                            possible > 0,
                            possible
                    );
                })
                .toList();

        Integer totalPages = (int) Math.ceil((double) total / size);

        return new PageResponseDto<>(
                content,
                total,
                page,
                size,
                totalPages
        );
    }

    @Transactional
    public int calculatePossible(ProductsEntity product) {
        int possible = Integer.MAX_VALUE;

        for (ProductsRawMaterialsEntity rel : product.getRawMaterial()) {
            System.out.println("Relação: " + rel.getRawMaterial().toString());

            int stock = rel.getRawMaterial().getQuantity();
            int required = rel.getQuantity();

            int canMake = stock / required;
            possible = Math.min(possible, canMake);
        }

        return possible == Integer.MAX_VALUE ? 0 : possible;
    }
}