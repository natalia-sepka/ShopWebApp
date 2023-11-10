import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { AlertComponent } from './components/alert/alert.component';

@NgModule({
  declarations: [AlertComponent],
  imports: [CommonModule],
  exports: [CommonModule, MaterialModule, ReactiveFormsModule, AlertComponent],
})
export class SharedModule {}
