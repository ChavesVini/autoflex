import type { PageResponseDto } from "../types/PageResponseDto";
import { api } from "./api";

export interface Product {
  id: number;
  name: string;
  price: number;
}

export const getAllProducts = async (
  page: number,
  size: number,
  search?: string
): Promise<PageResponseDto<Product>> => {

  let url = `/product/get-all?page=${page}&size=${size}`;

  if (search && search.trim() !== "") {
    url += `&name=${encodeURIComponent(search)}`;
  }

  const response = await api.get(url);
  return response.data;
};

export const getSpecificProduct = async (id: number): Promise<Product> => {
  const response = await api.get(`/product/get/${id}`);
  return response.data;
};

export const createProduct = async (
  data: Omit<Product, "id">
) => {
  const response = await api.post("/product/register", data);
  return response.data;
};

export const updateProduct = async (
  id: number,
  data: Omit<Product, "id">
) => {
  const response = await api.put(`/product/update/${id}`, data);
  return response.data;
};

export const deleteProduct = async (id: number) => {
  await api.delete(`/product/delete/${id}`);
};