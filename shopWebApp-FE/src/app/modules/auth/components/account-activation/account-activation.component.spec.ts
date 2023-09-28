import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountAxctivationComponent } from './account-activation.component';

describe('AccountAxctivationComponent', () => {
  let component: AccountAxctivationComponent;
  let fixture: ComponentFixture<AccountAxctivationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccountAxctivationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AccountAxctivationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
