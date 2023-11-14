import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';
import { ProductsService } from '../../../../core/services/products.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
})
export class ProductDetailsComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private productsService: ProductsService,
  ) {}
  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((paramMap) => {
          const [name, date] = (paramMap.get('id') as string).split('-');
          return this.productsService.getProduct(name, date);
        }),
      )
      .subscribe({
        next: (product) => {
          console.log(product);
        },
      });
  }
}
