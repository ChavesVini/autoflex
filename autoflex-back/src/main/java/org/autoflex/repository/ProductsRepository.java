package org.autoflex.repository;

import org.autoflex.entity.ProductsEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductsRepository implements PanacheRepository<ProductsEntity> {}