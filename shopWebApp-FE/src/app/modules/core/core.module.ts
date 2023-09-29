import { NgModule } from '@angular/core';
import { HeaderComponent } from './components/header/header.component';
import { SharedModule } from '../shared/shared.module';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [HeaderComponent],
  imports: [SharedModule, RouterLink, RouterLinkActive, HttpClientModule],
  exports: [HeaderComponent],
})
export class CoreModule {}
