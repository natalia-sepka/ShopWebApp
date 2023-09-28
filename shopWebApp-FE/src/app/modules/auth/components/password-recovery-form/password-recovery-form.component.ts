import { Component, OnInit } from '@angular/core';
import { FormService } from '../../../core/services/form.service';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-password-recovery-form',
  templateUrl: './password-recovery-form.component.html',
  styleUrls: ['./password-recovery-form.component.scss'],
})
export class PasswordRecoveryFormComponent implements OnInit {
  passwordsForm = this.formService.initPasswordsForm();

  constructor(
    private formService: FormService,
    private route: ActivatedRoute,
  ) {}

  getErrorMessage(control: FormControl<string>): string {
    return this.formService.getErrorMessage(control);
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (param) => console.log(param.get('uid')),
    });
  }
}
