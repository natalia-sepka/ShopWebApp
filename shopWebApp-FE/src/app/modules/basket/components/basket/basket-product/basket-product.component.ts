import { Component, Input } from '@angular/core';
import { BasketProduct } from '../../../../core/models/basket.module';

@Component({
  selector: 'app-basket-product',
  templateUrl: './basket-product.component.html',
  styleUrls: ['./basket-product.component.scss'],
})
export class BasketProductComponent {
  @Input() basketProduct!: BasketProduct;
}
