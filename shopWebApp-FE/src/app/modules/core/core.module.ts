import { NgModule } from '@angular/core';
import { HeaderComponent } from './components/header/header.component';
import { SharedModule } from '../shared/shared.module';
import { RouterLink, RouterLinkActive } from '@angular/router';

@NgModule({
  declarations: [HeaderComponent],
  imports: [SharedModule, RouterLink, RouterLinkActive],
  exports: [HeaderComponent],
})
export class CoreModule {}
