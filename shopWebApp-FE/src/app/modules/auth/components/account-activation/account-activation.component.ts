import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { AuthService } from '../../../core/services/auth.service';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-account-activation',
  templateUrl: './account-activation.component.html',
  styleUrls: ['./account-activation.component.scss'],
})
export class AccountActivationComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private notifierService: NotifierService,
    private router: Router,
  ) {}

  errorMsg: null | string = null;

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((params) =>
          this.authService.activateAccount(params.get('uid') as string),
        ),
      )
      .subscribe({
        next: (response) => {
          this.router.navigate(['/login']);
          this.notifierService.notify('success', response.message);
        },
        error: (err) => {
          this.errorMsg = err;
        },
      });
  }
}
