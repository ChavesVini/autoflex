package org.autoflex.controller;

import org.autoflex.service.ProductsRawMaterialsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product-raw-material")
public class ProductsRawMaterialsController {
    private final ProductsRawMaterialsService productsRawMaterialsService;

    @Inject
    public ProductsRawMaterialsController(ProductsRawMaterialsService productsRawMaterialsService) {
        this.productsRawMaterialsService = productsRawMaterialsService;
    }

    @GET
    @Path("/production-possibilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsProductionPossibilities(
            @QueryParam("name") String name,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        var products = productsRawMaterialsService.getProductsProductionPossibilities(name, page, size);

        return Response.ok(products).build();
    }
}