package org.autoflex.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class RawMaterialsControllerTest {

    private static final String BASE_PATH = "/raw-material";

    private Long createRawMaterial(String name, int quantity) {
        return given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "%s",
                          "quantity": %d
                        }
                        """.formatted(name, quantity))
        .when()
                .post(BASE_PATH + "/register")
        .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    @DisplayName("Should create raw material successfully")
    void shouldCreateRawMaterial() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "Iron",
                          "quantity": 10
                        }
                        """)
        .when()
                .post(BASE_PATH + "/register")
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Iron"))
                .body("quantity", equalTo(10));
    }

    @Test
    @DisplayName("Should return 404 when raw material not found")
    void shouldReturn404WhenRawMaterialNotFound() {
        given()
        .when()
                .get(BASE_PATH + "/get/999999")
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should return paginated raw materials list")
    void shouldReturnAllRawMaterials() {
        createRawMaterial("Copper", 20);

        given()
                .queryParam("page", 0)
                .queryParam("size", 5)
        .when()
                .get(BASE_PATH + "/get-all")
        .then()
                .statusCode(200)
                .body("content", not(empty()));
    }

    @Test
    @DisplayName("Should delete raw material successfully")
    void shouldDeleteRawMaterial() {
        Long id = createRawMaterial("Aluminum", 15);

        given()
        .when()
                .delete(BASE_PATH + "/delete/" + id)
        .then()
                .statusCode(204);
    }
}