import { Component } from '@angular/core';
import { ImageService } from '../../../../../core/services/image.service';
import { Image } from '../../../../../core/models/image.model';
import { FormService } from '../../../../../core/services/form.service';
import { FormControl, FormGroup } from '@angular/forms';
import { PostProduct } from '../../../../../core/models/forms.model';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { CategoriesService } from '../../../../../core/services/categories.service';
import { BehaviorSubject } from 'rxjs';
import { Category } from '../../../../../core/models/categories.model';
import { AddProductData } from '../../../../../core/models/products.model';
import { ProductsService } from '../../../../../core/services/products.service';

@Component({
  selector: 'app-add-product-form',
  templateUrl: './add-product-form.component.html',
  styleUrls: ['./add-product-form.component.scss'],
})
export class AddProductFormComponent {
  config: AngularEditorConfig = this.imageService.config;
  // does it make sense?
  selectedFile: File | string = '';

  fileName = '';
  imageUrls: Image[] = [];
  errorImageUploadMsg: string | null = null;

  categories: BehaviorSubject<Category[]> = this.categoriesService.categories;

  addProduct: FormGroup<PostProduct> = this.formService.initAddProductForm();

  errorAddProductMsg: string | null = null;
  successAddProductMsg: string | null = null;

  constructor(
    private imageService: ImageService,
    private formService: FormService,
    private categoriesService: CategoriesService,
    private productService: ProductsService,
  ) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] as File;

    if (this.selectedFile) {
      this.fileName = this.selectedFile.name;
    }
  }

  uploadFile() {
    this.errorImageUploadMsg = null;
    const formData = new FormData();

    formData.append('multipartFile', this.selectedFile);

    this.imageService.addImage(formData).subscribe({
      next: (response) => {
        this.imageUrls = [...this.imageUrls, { ...response }];
      },
      error: (err) => {
        this.errorImageUploadMsg = err;
      },
    });
  }

  setActiveImageUrls(imageArr: Image[]) {
    this.imageUrls = [...imageArr];
  }

  get controls() {
    return this.addProduct.controls;
  }

  getErrorMessage(control: FormControl<string>) {
    return this.formService.getErrorMessage(control);
  }

  addNewProduct() {
    const formValue = this.addProduct.getRawValue();

    const imagesUuid = this.imageUrls.map((url) => {
      const [, uuid] = url.url.split('uuid=');
      return uuid;
    });

    const addProductData: AddProductData = {
      ...formValue,
      price: Number(formValue.price),
      imagesUuid,
    };
    this.productService.addProduct(addProductData).subscribe({
      next: () => {
        this.addProduct.reset();
        this.imageUrls = [];
        this.successAddProductMsg = 'New product added.';
      },
      error: (err) => {
        this.errorAddProductMsg = err;
      },
    });
  }
}
