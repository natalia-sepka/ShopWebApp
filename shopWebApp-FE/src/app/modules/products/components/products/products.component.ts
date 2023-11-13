import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ProductsService } from '../../../core/services/products.service';
import { PrimitiveProduct } from '../../../core/models/products.model';
import { MatPaginator } from '@angular/material/paginator';
import {
  debounceTime,
  distinctUntilChanged,
  map,
  Observable,
  Subscription,
  switchMap,
} from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent implements OnInit, AfterViewInit, OnDestroy {
  products: PrimitiveProduct[] = [];
  totalCount = 0;
  sub = new Subscription();

  errorMsg: string | null = null;
  searchControl = new FormControl<string>('');
  sortControl = new FormControl<string>('');
  orderControl = new FormControl<string>('');
  filteredOptions!: Observable<PrimitiveProduct[]>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private productService: ProductsService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.filteredOptions = this.searchControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((value) => this.productService.getProducts(1, 10, value)),
      map(({ products }) => {
        return [...products];
      }),
    );
  }

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

          const productName = queryMap.get('name')
            ? queryMap.get('name')
            : null;

          const sortElement = queryMap.get('sort_by')
            ? queryMap.get('sort_by')
            : null;

          const order = queryMap.get('sort') ? queryMap.get('sort') : null;

          const category = queryMap.get('category')
            ? queryMap.get('category')
            : null;

          return this.productService.getProducts(
            pageIndex,
            itemsPerPage,
            productName,
            sortElement,
            order,
            category,
          );
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
          this.navigateToSearchParams();
        },
      }),
    );
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  searchProducts() {
    this.paginator.pageIndex = 0;
    this.paginator.pageSize = 5;
    this.navigateToSearchParams();
  }

  navigateToSearchParams() {
    const queryParams: { [key: string]: string | number } = {
      page: this.paginator.pageIndex + 1,
      limit: this.paginator.pageSize,
    };

    const category = this.route.snapshot.queryParamMap.get('category');

    if (category) {
      queryParams['category'] = category;
    }

    if (this.searchControl.value) {
      queryParams['name'] = this.searchControl.value;
    }

    if (this.sortControl.value) {
      queryParams['sort_by'] = this.sortControl.value;
    }

    if (this.orderControl.value) {
      queryParams['sort'] = this.orderControl.value;
    }

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams,
    });
  }
}
