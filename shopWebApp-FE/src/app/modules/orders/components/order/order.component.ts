import { Component, OnInit } from '@angular/core';
import { switchMap } from 'rxjs';
import { OrdersService } from '../../../core/services/orders.service';
import { ActivatedRoute } from '@angular/router';
import { GetOrderResponse } from '../../../core/models/order.model';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
})
export class OrderComponent implements OnInit {
  errorMsg: null | string = null;
  order!: GetOrderResponse;

  constructor(
    private route: ActivatedRoute,
    private ordersService: OrdersService,
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((paramMap) => {
          const uuid = paramMap.get('uuid') as string;
          return this.ordersService.getOrder(uuid);
        }),
      )
      .subscribe({
        next: (order) => {
          this.order = { ...order };
        },
        error: (err) => {
          this.errorMsg = err;
        },
      });
  }
}