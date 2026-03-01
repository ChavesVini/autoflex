package org.autoflex.repository;

import java.util.List;

import org.autoflex.entity.ProductsRawMaterialsEntity;
import org.autoflex.entity.dto.ProductRawMaterialNamesDto;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductsRawMaterialsRepository implements PanacheRepository<ProductsRawMaterialsEntity> {

    public List<ProductRawMaterialNamesDto> findByProductId(Long productId) {
        return getEntityManager().createQuery(
            "SELECT new org.autoflex.entity.dto.ProductRawMaterialNamesDto(" +
            "prm.id, prm.product.id, rm.id, rm.name, prm.quantity) " +
            "FROM ProductsRawMaterialsEntity prm " +
            "JOIN prm.rawMaterial rm " + 
            "WHERE prm.product.id = :productId", ProductRawMaterialNamesDto.class) // Força o tipo aqui
            .setParameter("productId", productId)
            .getResultList();
    }

    public List<ProductsRawMaterialsEntity> findEntitiesByProductId(Long productId) {
        return list("product.id", productId);
    }
}