import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { AddCategoryFormComponent } from './components/admin/add-category-form/add-category-form.component';
import { ManageProductsComponent } from './components/admin/manage-products/manage-products.component';
import { AdminGuard } from '../core/guards/admin.guard';

const routes: Routes = [
  {
    path: 'manage',
    component: AdminComponent,
    canActivate: [AdminGuard],
    children: [
      {
        path: 'categories',
        component: AddCategoryFormComponent,
      },
      {
        path: 'products',
        component: ManageProductsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministrationRoutingModule {}
