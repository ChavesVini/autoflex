package org.autoflex.service;

public class ProductsRawMaterialsService {
    public void getRawMaterialByCode(String code) {
        System.out.println("Getting raw material with code: " + code);
    }

    public void getProductsByAvailableRawMaterials(String materials) {
        System.out.println("Getting products with available raw materials: " + materials);
    }
}
