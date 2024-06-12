import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { PostOrderBody, PostOrderResponse } from '../models/order.model';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  apiUrl = `${environment.apiUrl}/order`;

  constructor(private http: HttpClient) {}

  addOrder(body: PostOrderBody): Observable<PostOrderResponse> {
    return this.http
      .post<PostOrderResponse>(`${this.apiUrl}`, body, {
        withCredentials: true,
      })
      .pipe(
        tap((resp) => {
          window.location.href = resp.redirectUri;
        }),
      );
  }
}
