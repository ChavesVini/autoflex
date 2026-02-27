export interface PageResponseDto<T> {
  content: T[];
  totalElements: number;
  page: number;
  size: number;
  totalPages: number;
}