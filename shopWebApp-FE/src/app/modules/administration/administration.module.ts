import { NgModule } from '@angular/core';

import { AdministrationRoutingModule } from './administration-routing.module';
import { AdminComponent } from './components/admin/admin.component';
import { AddCategoryFormComponent } from './components/admin/add-category-form/add-category-form.component';
import { ManageProductsComponent } from './components/admin/manage-products/manage-products.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    AdminComponent,
    AddCategoryFormComponent,
    ManageProductsComponent,
  ],
  imports: [
    AdministrationRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    SharedModule,
  ],
})
export class AdministrationModule {}
