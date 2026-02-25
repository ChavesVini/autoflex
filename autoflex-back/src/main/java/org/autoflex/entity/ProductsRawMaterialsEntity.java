package org.autoflex.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    
    @Column(name ="product_id")
    private Integer productId;
    
    @Column(name ="raw_materials_id")
    private Integer rawMaterialsId;

    @Column(name ="quantity")
    private Integer quantity;
}
