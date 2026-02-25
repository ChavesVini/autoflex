package org.autoflex.repository;

import org.autoflex.entity.ProductsRawMaterialsEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductsRawMaterialsRepository implements PanacheRepository<ProductsRawMaterialsEntity> {}