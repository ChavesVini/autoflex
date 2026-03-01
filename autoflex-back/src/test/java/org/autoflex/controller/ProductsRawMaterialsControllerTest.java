package org.autoflex.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ProductsRawMaterialsControllerTest {

    @Test
    void shouldGetMaterialsByProduct() {
        given()
        .when()
            .get("/product-raw-materials/product/1")
        .then()
            .statusCode(200);
    }

    @Test
    void shouldGetProductionPossibilities() {
        given()
            .queryParam("page", 0)
            .queryParam("size", 5)
        .when()
            .get("/product-raw-materials/production-possibilities")
        .then()
            .statusCode(200);
    }
}