package org.autoflex.repository;

import org.autoflex.entity.RawMaterialsEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RawMaterialsRepository implements PanacheRepository<RawMaterialsEntity> {}