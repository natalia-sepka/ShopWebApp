import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DleteProductDialogComponent } from './delete-product-dialog.component';

describe('DleteProductDialogComponent', () => {
  let component: DleteProductDialogComponent;
  let fixture: ComponentFixture<DleteProductDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DleteProductDialogComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DleteProductDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
