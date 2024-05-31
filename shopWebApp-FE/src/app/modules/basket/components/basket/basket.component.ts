import { Component } from '@angular/core';
import { BasketProduct } from '../../../core/models/basket.module';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss'],
})
export class BasketComponent {
  basketProducts: BasketProduct[] = [
    {
      name: 'Testowy produkt',
      price: 150,
      imageUrl: 'https://imgur.com/kHeKKij.jpg',
      quantity: 2,
      uuid: 'test',
      summaryPrice: 300,
    },
    {
      name: 'Testowy produkt 2',
      price: 150,
      imageUrl: 'https://imgur.com/kHeKKij.jpg',
      quantity: 2,
      uuid: 'test',
      summaryPrice: 300,
    },
  ];
  summaryPrice = 600;
}
