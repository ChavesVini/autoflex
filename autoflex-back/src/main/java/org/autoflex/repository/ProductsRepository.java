package org.autoflex.repository;

import java.util.List;

import org.autoflex.entity.ProductsEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductsRepository implements PanacheRepository<ProductsEntity> {
    public List<ProductsEntity> findByName(String text) {
        return find("LOWER(name) LIKE ?1",
            "%" + text.toLowerCase() + "%")
            .list();
    }
}