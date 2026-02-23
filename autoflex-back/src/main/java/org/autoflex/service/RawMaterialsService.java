package org.autoflex.service;

public class RawMaterialsService {
    public void registerRawMaterial(String code, String name, Integer quantityStock) {
        System.out.println("Raw material registered: " + code + ", " + name + ", " + quantityStock);
    }   

    public void getRawMaterialByCode(String code) {
        System.out.println("Getting raw material with code: " + code);
    }

    

}
