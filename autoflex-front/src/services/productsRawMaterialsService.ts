import type { PageResponseDto } from "../types/PageResponseDto";
import { api } from "./api";

export interface Products {
  id: number;
  name: string;
  price: number;
}

export const getProduction = async (
  page: number,
  size: number,
  search?: string
): Promise<PageResponseDto<Products>> => {

  let url = `/product-raw-material/production-possibilities?page=${page}&size=${size}`;

  if (search && search.trim() !== "") {
    url += `&name=${encodeURIComponent(search)}`;
  }

  const response = await api.get(url);
  return response.data;
};