package org.autoflex.controller;

import org.autoflex.entity.dto.ProductsUpdateDto;
import org.autoflex.service.ProductsRawMaterialsService;
import org.autoflex.service.ProductsService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.PathParam;

@Path("/product")
public class ProductsController {
    private final ProductsService productsService;
    private final ProductsRawMaterialsService productsRawMaterialsService;

    @Inject
    public ProductsController(ProductsService productsService, ProductsRawMaterialsService productsRawMaterialsService) {
        this.productsService = productsService;
        this.productsRawMaterialsService = productsRawMaterialsService;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response registerProduct(@Valid ProductsUpdateDto registerProductsDto) {
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
        return Response.ok(product).build();
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(
            @QueryParam("name") String name,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        var products = productsService.getAllProducts(name, page, size);

        if (products == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(products).build();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(
        @PathParam("id") Long code,
        @Valid ProductsUpdateDto updateProductsDto
    ) {
        var products = productsService.updateProduct(updateProductsDto, code);

        if (products == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(products).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProduct(
        @PathParam("id") Long code
    ) {
        productsRawMaterialsService.validateProductIsNotAssociated(code);
        
        productsService.deleteProduct(code);

        return Response.noContent().build();

    }
}
