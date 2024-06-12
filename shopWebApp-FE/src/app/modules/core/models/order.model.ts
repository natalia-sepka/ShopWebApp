import { Customer } from './customer.model';
import { Address } from './address.model';
import { PostDelivery } from './delivery.model';

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
