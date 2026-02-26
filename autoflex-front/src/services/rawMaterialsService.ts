import type { PageResponseDto } from "../type/PageResponseDto";
import { api } from "./api";

export interface RawMaterial {
  id: number;
  name: string;
  quantity: number;
}

export const getAllRawMaterials = async (page: number, size: number): Promise<PageResponseDto<RawMaterial>> => {
  const response = await api.get(`/raw-material/get-all?page=${page}&size=${size}`);
  return response.data;
};

export const getSpecificRawMaterial = async (id: number): Promise<RawMaterial> => {
  const response = await api.get(`/raw-material/get/${id}`);
  return response.data;
};

export const createRawMaterial = async (
  data: Omit<RawMaterial, "id">
) => {
  const response = await api.post("/raw-material/register", data);
  return response.data;
};

export const updateRawMaterial = async (
  id: number,
  data: Omit<RawMaterial, "id">
) => {
  const response = await api.put(`/raw-material/update/${id}`, data);
  return response.data;
};

export const deleteRawMaterial = async (id: number) => {
  await api.delete(`/raw-material/delete/${id}`);
};