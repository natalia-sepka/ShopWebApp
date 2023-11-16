import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministrationRoutingModule } from './administration-routing.module';
import { AdminComponent } from './components/admin/admin.component';
import { AddCategoryFormComponent } from './components/admin/add-category-form/add-category-form.component';
import { ManageProductsComponent } from './components/admin/manage-products/manage-products.component';

@NgModule({
  declarations: [
    AdminComponent,
    AddCategoryFormComponent,
    ManageProductsComponent,
  ],
  imports: [CommonModule, AdministrationRoutingModule],
})
export class AdministrationModule {}
