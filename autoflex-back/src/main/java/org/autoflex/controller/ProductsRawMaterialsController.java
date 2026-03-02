package org.autoflex.controller;

import org.autoflex.entity.dto.ProductRawMaterialNamesDto;
import org.autoflex.service.ProductsRawMaterialsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/product-raw-materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsRawMaterialsController {

    @Inject
    ProductsRawMaterialsService service;

    @GET
    @Path("/product/{productId}")
    public Response getMaterialsByProduct(@PathParam("productId") Long productId) {
        List<ProductRawMaterialNamesDto> materials = service.findMaterialsByProductId(productId);
        return Response.ok(materials).build();
    }

    @PUT
    @Path("/product/{productId}/sync")
    public Response syncMaterials(@PathParam("productId") Long productId, List<ProductRawMaterialNamesDto> dtos) {
        try {
            service.updateProductMaterials(productId, dtos);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Erro ao sincronizar materiais: " + e.getMessage())
                           .build();
        }
    }

    @GET
    @Path("/production-possibilities")
    public Response getProductsProductionPossibilities(
            @QueryParam("name") String name,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size
    ) {
        var result = service.getProductsProductionPossibilities(name, page, size);
        return Response.ok(result).build();
    }
}