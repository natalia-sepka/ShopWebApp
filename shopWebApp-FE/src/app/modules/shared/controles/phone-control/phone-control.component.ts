import { Component, OnDestroy } from '@angular/core';
import { ControlValueAccessor, FormControl, Validators } from '@angular/forms';
import { combineLatest, Subscription } from 'rxjs';

@Component({
  selector: 'app-phone-control',
  templateUrl: './phone-control.component.html',
  styleUrls: ['./phone-control.component.scss'],
})
export class PhoneControlComponent implements ControlValueAccessor, OnDestroy {
  numberPrefixControl = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(2),
  ]);
  numberControl = new FormControl('', [
    Validators.required,
    Validators.minLength(9),
    Validators.maxLength(9),
  ]);
  sub = new Subscription();

  constructor() {
    this.sub.add(
      combineLatest([
        this.numberPrefixControl.valueChanges,
        this.numberControl.valueChanges,
      ]).subscribe(([prefix, number]) => {
        if (prefix && number) {
          this.onChange(`+${prefix}${number}`);
        } else {
          this.onChange(null);
        }
      }),
    );
  }

  onChange = (value: string | null) => {};

  onTouch = () => {};

  //  zostanie wywołana z funkcją, którą należy wywołać by zmienić wartość kontrolki
  registerOnChange(fn: () => void): void {
    this.onChange = fn;
  }

  // zostanie wywołana z funkcją, którą należy wywołać by oznaczyć kontrolkę jako touched
  registerOnTouched(fn: () => void): void {
    this.onTouch = fn;
  }

  // bedzie uzywane kiedy ktos da formControl.enable() albo disable() na kontrolce
  setDisabledState(isDisabled: boolean): void {
    if (isDisabled) {
      this.numberControl.disable();
      this.numberPrefixControl.disable();
    } else {
      this.numberControl.enable();
      this.numberPrefixControl.enable();
    }
  }

  // zostanie uzyte kiedy ktos uzyje np. formControl.setValue()
  writeValue(value: string): void {
    const valueWithoutPlus = value.replace('+', '');
    const prefix = valueWithoutPlus.slice(0, 2);
    const number = valueWithoutPlus.slice(2);

    this.numberPrefixControl.setValue(prefix);
    this.numberControl.setValue(number);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
