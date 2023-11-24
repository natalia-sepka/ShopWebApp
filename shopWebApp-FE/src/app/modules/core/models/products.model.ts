export interface PrimitiveProduct {
  name: string;
  price: number;
  createAt: string;
  imageUrl: string;
  mainDesc: string;
}

export interface Product extends Omit<PrimitiveProduct, 'imageUrl'> {
  uid: string;
  activate: boolean;
  descHtml: string;
  mainDesc: string;
  imageUrls: string[];
  parameters: string;
  categoryDTO: {
    name: string;
    shortId: string;
  };
}

export interface GetProductsResponse {
  products: PrimitiveProduct[];
  totalCount: number;
}

export interface AddProductData {
  name: string;
  mainDesc: string;
  descHtml: string;
  price: number;
  imagesUuid: string[];
  parameters: string;
  category: string;
}

export interface PostProductResponse {
  timestamp: string;
  message: string;
}
