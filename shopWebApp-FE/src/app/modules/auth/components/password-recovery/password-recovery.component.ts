import { Component, OnInit } from '@angular/core';
import { FormService } from '../../../core/services/form.service';
import { FormControl, FormGroup } from '@angular/forms';
import { PasswordRecoveryForm } from '../../../core/models/forms.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss'],
})
export class PasswordRecoveryComponent {
  passwordRecoveryForm: FormGroup<PasswordRecoveryForm> =
    this.formService.initPasswordRecoveryForm();

  constructor(
    private formService: FormService,
    private route: ActivatedRoute,
  ) {}

  getErrorMessage(control: FormControl<string>) {
    return this.formService.getErrorMessage(control);
  }
}
