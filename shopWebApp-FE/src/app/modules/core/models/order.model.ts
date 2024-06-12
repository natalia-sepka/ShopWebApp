import { Customer } from './customer.model';
import { Address } from './address.model';
import { GetDelivery, PostDelivery } from './delivery.model';
import { BasketProduct } from './basket.module';

export interface PostOrderBody {
  customerDetails: Customer;
  address: Address;
  deliver: PostDelivery;
}

export interface PostOrderResponse {
  status: {
    statusCode: string;
  };
  redirectUri: string;
  orderId: string;
  extOrderId: string;
}

export interface GetOrderResponse {
  uuid: string;
  orders: string;
  status: string;
  customerDetails: Customer;
  address: Address;
  deliver: GetDelivery;
  items: BasketProduct[];
  summaryPrice: number;
}

export type GetOrdersResponse = Omit<
  GetOrderResponse,
  'items' | 'summaryPrice'
>;
