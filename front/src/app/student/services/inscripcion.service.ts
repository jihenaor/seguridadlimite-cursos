import { Injectable } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms'

@Injectable()
export class InscripcionService {
  mainForm: UntypedFormGroup;

  constructor(private fb: UntypedFormBuilder) {
    this.mainForm = this.fb.group({
      componente1: this.fb.group({}),
      componente2: this.fb.group({})
    });
  }
}
