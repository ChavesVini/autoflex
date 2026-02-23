package org.autoflex.entity;

import org.autoflex.entity.dto.ProductsDto;
import org.autoflex.entity.dto.ProductsRawMaterialsDto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_raw_materials")
public class ProductsRawMaterials extends PanacheEntity {
    @Column(name ="product_id")
    private String productId;
    @Column(name ="raw_materials_id")
    private String rawMaterialsId;
    @Column(name ="quantity")
    private Integer quantity;
    
    public ProductsRawMaterials(ProductsRawMaterialsDto products) {
        this.productId = products.productId();
        this.rawMaterialsId = products.rawMaterialsId();
        this.quantity = products.quantity();
    }

    public ProductsRawMaterials() {}
}
