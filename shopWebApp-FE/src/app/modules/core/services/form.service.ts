import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  AddCategoryForm,
  LoginForm,
  PasswordRecoveryForm,
  PasswordsForm,
  PostProduct,
  RegisterForm,
} from '../models/forms.model';
import { equivalentValidator } from '../../shared/validators/equivalent.validator';

@Injectable({
  providedIn: 'root',
})
export class FormService {
  initAddCategoryForm(): FormGroup<AddCategoryForm> {
    return new FormGroup({
      name: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
    });
  }
  initAddProductForm(): FormGroup<PostProduct> {
    return new FormGroup({
      name: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
      mainDesc: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
      descHtml: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
      price: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
      category: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
      parameters: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
    });
  }
  initPasswordRecoveryForm(): FormGroup<PasswordRecoveryForm> {
    return new FormGroup({
      email: new FormControl('', {
        validators: [Validators.required, Validators.email],
        nonNullable: true,
      }),
    });
  }

  initPasswordsForm(): FormGroup<PasswordsForm> {
    return new FormGroup(
      {
        password: new FormControl('', {
          validators: [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(50),
          ],
          nonNullable: true,
        }),
        repeatedPassword: new FormControl('', {
          validators: [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(50),
          ],
          nonNullable: true,
        }),
      },
      { validators: [equivalentValidator('password', 'repeatedPassword')] },
    );
  }

  initLoginForm(): FormGroup<LoginForm> {
    return new FormGroup({
      login: new FormControl('', {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(50),
        ],
        nonNullable: true,
      }),
      password: new FormControl('', {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(50),
        ],
        nonNullable: true,
      }),
    });
  }

  initRegisterForm(): FormGroup<RegisterForm> {
    return new FormGroup({
      email: new FormControl('', {
        validators: [Validators.required, Validators.email],
        nonNullable: true,
      }),
      login: new FormControl('', {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(50),
        ],
        nonNullable: true,
      }),
      password: new FormControl('', {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(50),
        ],
        nonNullable: true,
      }),
      repeatedPassword: new FormControl('', {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(50),
        ],
        nonNullable: true,
      }),
    });
  }

  getErrorMessage(control: FormControl): string {
    if (control.hasError('required')) {
      return 'This field is required';
    }
    if (control.hasError('minlength')) {
      return `Enter at least ${control.errors?.['minlength']?.requiredLength} characters`;
    }
    if (control.hasError('maxlength')) {
      return `Enter no more than ${control.errors?.['maxlength']?.requiredLength} characters`;
    }
    if (control.hasError('email')) {
      return 'Enter valid e-mail address';
    }
    if (control.hasError('passwordNotEqual')) {
      return 'Passwords must be equal';
    }
    return '';
  }
}
