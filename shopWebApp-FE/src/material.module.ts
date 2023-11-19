import { NgModule } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  exports: [
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatExpansionModule,
    MatIconModule,
  ],
})
export class MaterialModule {}
