package org.autoflex.repository;

import java.util.List;

import org.autoflex.entity.RawMaterialsEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RawMaterialsRepository implements PanacheRepository<RawMaterialsEntity> {
    public List<RawMaterialsEntity> findByName(String text) {
        return find("LOWER(name) LIKE ?1",
            "%" + text.toLowerCase().trim() + "%")
            .list();
    }
}