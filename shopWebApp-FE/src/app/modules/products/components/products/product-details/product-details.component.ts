import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';
import { ProductsService } from '../../../../core/services/products.service';
import { Product } from '../../../../core/models/products.model';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
})
export class ProductDetailsComponent implements OnInit {
  product: Product | null = null;
  parameters: string | null = null;
  htmlContent: null | SafeHtml = null;
  constructor(
    private route: ActivatedRoute,
    private productsService: ProductsService,
    private sanitizer: DomSanitizer,
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
          this.product = { ...product };
          this.htmlContent = this.sanitizer.bypassSecurityTrustHtml(
            product.descHtml,
          );
          this.parameters = product.parameters;
        },
      });
  }
}
