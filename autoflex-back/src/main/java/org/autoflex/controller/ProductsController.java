package org.autoflex.controller;

import org.autoflex.entity.dto.RegisterProductsDto;
import org.autoflex.service.ProductsService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
}
