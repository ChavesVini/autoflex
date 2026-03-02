package org.autoflex.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductsControllerTest {

    private static Long createdProductId;

    private static final String BASE_PATH = "/product";

    @Test
    @Order(1)
    @DisplayName("Should create a product successfully")
    void shouldCreateProduct() {

    createdProductId =
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                "name": "Professional Product",
                "price": 150
                }
            """)
        .when()
            .post(BASE_PATH + "/register")
        .then()
            .statusCode(201)
            .extract()
            .jsonPath()
            .getLong("id");
    }

    @Test
    @Order(2)
    @DisplayName("Should retrieve created product by id")
    void shouldGetProductById() {

        given()
        .when()
            .get(BASE_PATH + "/get/" + createdProductId)
        .then()
            .statusCode(200)
            .body("id", equalTo(createdProductId.intValue()))
            .body("name", equalTo("Professional Product"))
            .body("price", equalTo(150));
    }

    @Test
    @Order(3)
    @DisplayName("Should return paginated list of products")
    void shouldReturnPaginatedProducts() {

        given()
            .queryParam("page", 0)
            .queryParam("size", 5)
        .when()
            .get(BASE_PATH + "/get-all")
        .then()
            .statusCode(200)
            .body("content", notNullValue())
            .body("content.size()", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(4)
    @DisplayName("Should delete product successfully")
    void shouldDeleteProduct() {

        given()
        .when()
            .delete(BASE_PATH + "/delete/" + createdProductId)
        .then()
            .statusCode(204);
    }

    @Test
    @Order(5)
    @DisplayName("Should return 404 when retrieving deleted product")
    void shouldReturnNotFoundAfterDeletion() {

        given()
        .when()
            .get(BASE_PATH + "/get/" + createdProductId)
        .then()
            .statusCode(404);
    }
}