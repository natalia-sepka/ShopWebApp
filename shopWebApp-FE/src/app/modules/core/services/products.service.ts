import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import {
  GetProductsResponse,
  PrimitiveProduct,
} from '../models/products.model';

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  apiUrl = `${environment.apiUrl}/product`;

  constructor(private http: HttpClient) {}

  getProducts(
    pageIndex = 1,
    itemsPerPage = 5,
  ): Observable<GetProductsResponse> {
    const params = new HttpParams()
      .append('_page', pageIndex)
      .append('_limit', itemsPerPage);
    return this.http
      .get<PrimitiveProduct[]>(`${this.apiUrl}`, {
        observe: 'response',
        params,
      })
      .pipe(
        map((response) => {
          if (!response.body) return { products: [], totalCount: 0 };
          const totalCount = Number(response.headers.get('X-Total-Count'));
          return { products: [...response.body], totalCount };
        }),
      );
  }
}
