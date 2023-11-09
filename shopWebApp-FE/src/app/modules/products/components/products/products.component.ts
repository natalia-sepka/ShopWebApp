import { Component } from '@angular/core';
import { Product } from '../../../core/models/products.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent {
  products: Product[] = [
    {
      uid: '1234',
      name: 'Product1',
      price: 200,
      imageUrl: 'https://i.imgur.com/kHeKKij.jpg',
      active: true,
      category: 'Category1',
      shortId: 1,
    },
    {
      uid: '12345',
      name: 'Product2',
      price: 201,
      imageUrl: 'https://i.imgur.com/kHeKKij.jpg',
      active: true,
      category: 'Category2',
      shortId: 12,
    },
    {
      uid: '123456',
      name: 'Product3',
      price: 202,
      imageUrl: 'https://i.imgur.com/kHeKKij.jpg',
      active: true,
      category: 'Category2',
      shortId: 123,
    },
    {
      uid: '1234567',
      name: 'Product4',
      price: 203,
      imageUrl: 'https://i.imgur.com/kHeKKij.jpg',
      active: true,
      category: 'Category1',
      shortId: 1234,
    },
  ];
}
