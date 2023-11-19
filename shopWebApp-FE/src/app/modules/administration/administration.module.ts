import { NgModule } from '@angular/core';

import { AdministrationRoutingModule } from './administration-routing.module';
import { AdminComponent } from './components/admin/admin.component';
import { AddCategoryFormComponent } from './components/admin/add-category-form/add-category-form.component';
import { ManageProductsComponent } from './components/admin/manage-products/manage-products.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { SharedModule } from '../shared/shared.module';
import { AddProductFormComponent } from './components/admin/manage-products/add-product-form/add-product-form.component';
import { DeleteProductFormComponent } from './components/admin/manage-products/delete-product-form/delete-product-form.component';
import { UploadedImagesComponent } from './components/admin/manage-products/add-product-form/uploaded-images/uploaded-images.component';

@NgModule({
  declarations: [
    AdminComponent,
    AddCategoryFormComponent,
    ManageProductsComponent,
    AddProductFormComponent,
    DeleteProductFormComponent,
    UploadedImagesComponent,
  ],
  imports: [
    AdministrationRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    SharedModule,
  ],
})
export class AdministrationModule {}
