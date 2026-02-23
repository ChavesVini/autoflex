package org.autoflex.service;

public class ProductsService {
    public void registerProduct(String code, String name, double price) {
        System.out.println("Product registered: " + code + ", " + name + ", " + price);
    }

    public void getProduct(String code) {
        System.out.println("Getting product with code: " + code);
    }

    public void updateProduct(String code, String name, double price) {
        System.out.println("Product updated: " + code + ", " + name + ", " + price);
    }
}