export interface PrimitiveProduct {
  name: string;
  price: number;
  createAt: string;
  imageUrl: string;
  mainDesc: string;
}

export interface GetProductsResponse {
  products: PrimitiveProduct[];
  totalCount: number;
}
