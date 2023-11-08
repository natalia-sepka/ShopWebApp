import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../../../../store/app.reducer';
import * as AuthActions from '../../../auth/store/auth.actions';
import { Observable } from 'rxjs';
import { User } from '../../models/auth.model';
import { selectAuthUser } from '../../../auth/store/auth.selectors';
import { Categories } from '../../models/categories.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  user$: Observable<User | null> = this.store.select(selectAuthUser);

  categories: Categories[] = [
    { name: 'Category1', shortId: 12345 },
    { name: 'Category2', shortId: 12344 },
  ];
  constructor(private store: Store<AppState>) {}
  logout() {
    this.store.dispatch(AuthActions.logout());
  }
}
