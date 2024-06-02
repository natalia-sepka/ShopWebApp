import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BasketProduct } from '../../../../core/models/basket.module';
import { BasketService } from '../../../../core/services/basket.service';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-basket-product',
  templateUrl: './basket-product.component.html',
  styleUrls: ['./basket-product.component.scss'],
})
export class BasketProductComponent {
  @Input() basketProduct!: BasketProduct;
  @Output() deleteProductUuid = new EventEmitter<string>();

  constructor(
    private basketService: BasketService,
    private notifierService: NotifierService,
  ) {}

  deleteProductFromBasket() {
    this.basketService
      .deleteProductFromBasket(this.basketProduct.uuid)
      .subscribe({
        next: () => {
          this.notifierService.notify(
            'success',
            'Successfully removed product from the basket.',
          );
          this.deleteProductUuid.emit(this.basketProduct.uuid);
        },
        error: (err) => {
          this.notifierService.notify(
            'warning',
            'Removing product from the basket has failed. Try again.',
          );
        },
      });
  }
}
