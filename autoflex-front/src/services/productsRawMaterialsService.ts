import type { PageResponseDto } from "../types/PageResponseDto";
import { api } from "./api";

export interface Products {
  id: number;
  name: string;
  price: number;
}

export interface ProductRawMaterialNamesDto {
  id?: number;
  productId?: number;
  rawMaterialId: number;
  rawMaterialName?: string;
  quantity: number;
}

export const getProduction = async (
  page: number,
  size: number,
  search?: string
): Promise<PageResponseDto<Products>> => {
  let url = `/product-raw-materials/production-possibilities?page=${page}&size=${size}`;

  if (search && search.trim() !== "") {
    url += `&name=${encodeURIComponent(search)}`;
  }

  const response = await api.get(url);
  return response.data;
};

export const getMaterialsByProduct = async (productId: number): Promise<ProductRawMaterialNamesDto[]> => {
  const response = await api.get(`/product-raw-materials/product/${productId}`);
  return response.data;
};

export const syncProductMaterials = async (
  productId: number, 
  materials: ProductRawMaterialNamesDto[]
): Promise<void> => {
  const payload = materials.map(m => ({
    productId: productId,
    rawMaterialId: m.rawMaterialId,
    rawMaterialName: m.rawMaterialName,
    quantity: m.quantity
  }));

  await api.put(`/product-raw-materials/product/${productId}/sync`, payload);
};