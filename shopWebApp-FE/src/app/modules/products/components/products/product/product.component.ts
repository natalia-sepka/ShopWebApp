import { Component, Input } from '@angular/core';
import { PrimitiveProduct } from '../../../../core/models/products.model';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss'],
})
export class ProductComponent {
  @Input() product!: PrimitiveProduct;

  getProductsDetailsUrl() {
    return `/product/${this.product.name}-${this.product.createAt.replaceAll(
      '-',
      '',
    )}`;
  }
}
