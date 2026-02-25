package org.autoflex.controller;

import org.autoflex.entity.dto.RegisterProductsDto;
import org.autoflex.service.ProductsService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.PathParam;

@Path("/product")
public class ProductsController {
    private final ProductsService productsService;

    @Inject
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response registerProduct(@Valid RegisterProductsDto registerProductsDto) {
        var createdProduct = productsService.registerProduct(registerProductsDto);
        return Response.status(Response.Status.CREATED)
        .entity(createdProduct)
        .build();
    }
    
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") Long code) {
        var product = productsService.getProduct(code);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(product).build();
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts(
        @QueryParam("page") int page,
        @QueryParam("size") int size
    ) {
        var products = productsService.getAllProducts(page, size);

        if (products == null || products.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(products).build();
    }
}
