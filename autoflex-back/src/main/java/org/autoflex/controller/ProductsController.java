package org.autoflex.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/hello78")
public class ProductsController {

    @GET
    public String hello() {
        return "Hello from Quarkus REST";
    }
}
