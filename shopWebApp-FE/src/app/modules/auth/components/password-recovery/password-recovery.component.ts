import { Component } from '@angular/core';
import { FormService } from '../../../core/services/form.service';
import { FormControl, FormGroup } from '@angular/forms';
import { PasswordRecoveryForm } from '../../../core/models/forms.model';
import { AuthService } from '../../../core/services/auth.service';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss'],
})
export class PasswordRecoveryComponent {
  passwordRecoveryForm: FormGroup<PasswordRecoveryForm> =
    this.formService.initPasswordRecoveryForm();

  errorMessage: null | string = null;

  constructor(
    private formService: FormService,
    private authService: AuthService,
    private notifierService: NotifierService,
  ) {}

  getErrorMessage(control: FormControl<string>) {
    return this.formService.getErrorMessage(control);
  }

  onPasswordRecovery() {
    this.authService
      .resetPassword(this.passwordRecoveryForm.getRawValue())
      .subscribe({
        next: () => {
          this.notifierService.notify(
            'success',
            'Email has been sent to given address',
          );
        },
        error: (err) => {
          this.errorMessage = err;
        },
      });
  }
}
