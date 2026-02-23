package org.autoflex.entity;

import org.autoflex.entity.dto.RawMaterialsDto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class RawMaterialsEntity extends PanacheEntity {
    @Column(name ="name")
    private String name;
    @Column(name ="quantity")
    private Integer quantity;

    public RawMaterialsEntity(RawMaterialsDto rawMaterials) {
        this.name = rawMaterials.name();
        this.quantity = rawMaterials.quantity();
    }

    public RawMaterialsEntity() {}
}
