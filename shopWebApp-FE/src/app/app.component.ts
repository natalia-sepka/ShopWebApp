import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import * as AuthActions from '../app/modules/auth/store/auth.actions';
import { AppState } from './store/app.reducer';
import { BasketService } from './modules/core/services/basket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'shopWebApp';
  constructor(
    private store: Store<AppState>,
    private basketService: BasketService,
  ) {}

  ngOnInit(): void {
    this.store.dispatch(AuthActions.autoLogin());

    this.basketService.getBasketProducts().subscribe({
      error: () => {
        // do nothing...
      },
    });
  }
}
