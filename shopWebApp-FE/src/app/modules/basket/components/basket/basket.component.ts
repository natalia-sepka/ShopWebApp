import { Component, OnInit } from '@angular/core';
import {
  BasketProduct,
  GetBasketResponse,
} from '../../../core/models/basket.module';
import { BasketService } from '../../../core/services/basket.service';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss'],
})
export class BasketComponent implements OnInit {
  basketProducts: BasketProduct[] = [];
  summaryPrice = 0;
  errorMessage: string | null = null;

  constructor(private basketService: BasketService) {}

  ngOnInit(): void {
    this.basketService.getBasketProducts().subscribe({
      next: (resp) => {
        if (resp.body !== null) {
          const basketResponse = resp.body as GetBasketResponse;
          this.basketProducts = [...basketResponse.basketProducts];
          this.summaryPrice = basketResponse.summaryPrice;
        }
      },
      error: (err) => {
        this.errorMessage = err;
      },
    });
  }

  deleteProduct(uuid: string) {
    this.basketProducts = this.basketProducts.filter(
      (product) => product.uuid !== uuid,
    );
  }
}
