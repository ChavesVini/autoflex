package org.autoflex.controller;

import org.autoflex.entity.dto.RawMaterialsDto;
import org.autoflex.service.RawMaterialsService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/raw-material")
public class RawMaterialsController {
    private final RawMaterialsService rawMaterialsService;

    @Inject
    public RawMaterialsController(RawMaterialsService rawMaterialsService) {
        this.rawMaterialsService = rawMaterialsService;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response registerRawMaterial(@Valid RawMaterialsDto registerRawMaterialsDto) {
        var createdProduct = rawMaterialsService.registerRawMaterial(registerRawMaterialsDto);
        return Response.status(Response.Status.CREATED)
        .entity(createdProduct)
        .build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") Long code) {
        var product = rawMaterialsService.getRawMaterial(code);

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
        var products = rawMaterialsService.getAllRawMaterials(page, size);

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
        @Valid RawMaterialsDto updateRawMaterialsDto
    ) {
        var products = rawMaterialsService.updateRawMaterial(updateRawMaterialsDto, code);

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
        rawMaterialsService.deleteRawMaterial(code);

        return Response.noContent().build();
    }
}
