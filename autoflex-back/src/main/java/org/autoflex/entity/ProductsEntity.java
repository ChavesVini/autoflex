package org.autoflex.entity;

import org.autoflex.entity.dto.ProductsDto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductsEntity extends PanacheEntity {
    @Column(name ="name")
    private String name;
    @Column(name ="price")
    private String price;

    public ProductsEntity(ProductsDto products) {
        this.name = products.name();
        this.price = products.price();
    }

    public ProductsEntity() {}
}
