import { NgModule } from '@angular/core';

import { BasketRoutingModule } from './basket-routing.module';
import { BasketComponent } from './components/basket/basket.component';
import { BasketProductComponent } from './components/basket/basket-product/basket-product.component';
import { SharedModule } from '../shared/shared.module';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CreateOrderComponent } from './components/create-order/create-order.component';
import { CustomerFormComponent } from './components/create-order/customer-form/customer-form.component';
import { AddressFormComponent } from './components/create-order/address-form/address-form.component';
import { DeliveryFormComponent } from './components/create-order/delivery-form/delivery-form.component';

@NgModule({
  declarations: [
    BasketComponent,
    BasketProductComponent,
    CreateOrderComponent,
    CustomerFormComponent,
    AddressFormComponent,
    DeliveryFormComponent,
  ],
  imports: [SharedModule, BasketRoutingModule, MatTooltipModule],
})
export class BasketModule {}
