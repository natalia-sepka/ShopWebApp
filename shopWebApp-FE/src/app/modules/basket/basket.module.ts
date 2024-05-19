import { NgModule } from '@angular/core';

import { BasketRoutingModule } from './basket-routing.module';
import { BasketComponent } from './components/basket/basket.component';
import { BasketProductComponent } from './components/basket/basket-product/basket-product.component';
import { SharedModule } from '../shared/shared.module';
import { MatTooltipModule } from '@angular/material/tooltip';

@NgModule({
  declarations: [BasketComponent, BasketProductComponent],
  imports: [SharedModule, BasketRoutingModule, MatTooltipModule],
})
export class BasketModule {}
