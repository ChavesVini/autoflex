package org.autoflex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products_raw_materials")
public class ProductsRawMaterialsEntity extends PanacheEntity {
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterialsEntity rawMaterial;

    @Column(name ="quantity")
    private Integer quantity;
}
