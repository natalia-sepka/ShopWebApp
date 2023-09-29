import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { StoreModule } from '@ngrx/store';
import { CoreModule } from './modules/core/core.module';
import { EffectsModule } from '@ngrx/effects';
import { AuthModule } from './modules/auth/auth.module';
import { authReducer } from './modules/auth/store/auth.reducer';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    CoreModule,
    AuthModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    StoreModule.forRoot({ auth: authReducer }),
    EffectsModule.forRoot([]),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
