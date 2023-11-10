import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { ProductsService } from '../../../core/services/products.service';
import { PrimitiveProduct } from '../../../core/models/products.model';
import { MatPaginator } from '@angular/material/paginator';
import { map, Subscription, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent implements AfterViewInit, OnDestroy {
  products: PrimitiveProduct[] = [];
  totalCount = 0;
  sub = new Subscription();
  errorMsg: string | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private productService: ProductsService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}
  ngAfterViewInit(): void {
    // this.productService.getProducts().subscribe({
    //   next: ({ products, totalCount }) => {
    //     this.products = [...products];
    //     this.totalCount = totalCount;
    //   },
    // });
    this.route.queryParamMap
      .pipe(
        switchMap((queryMap) => {
          const pageIndex = queryMap.get('page')
            ? Number(queryMap.get('page'))
            : 1;
          const itemsPerPage = queryMap.get('limit')
            ? Number(queryMap.get('limit'))
            : this.paginator.pageSize;
          return this.productService.getProducts(pageIndex, itemsPerPage);
        }),
        map(({ products, totalCount }) => {
          this.totalCount = totalCount;
          this.products = [...products];
        }),
      )
      .subscribe({
        error: (err) => {
          this.errorMsg = err;
        },
      });

    this.sub.add(
      this.paginator.page.subscribe({
        next: () => {
          const pageIndex = this.paginator.pageIndex + 1;
          const itemsPerPage = this.paginator.pageSize;
          this.router.navigate([], {
            relativeTo: this.route,
            queryParams: { page: pageIndex, limit: itemsPerPage },
          });
        },
      }),
    );
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
