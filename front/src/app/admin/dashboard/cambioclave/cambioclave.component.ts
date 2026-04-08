import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './../../../core/service/auth.service';

import { MyValidators } from './../../../utils/validators';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
    selector: 'app-cambioclave',
    templateUrl: './cambioclave.component.html',
    styleUrls: ['./cambioclave.component.scss'],
    imports: [
        RouterLink,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        NgIf,
        MatButtonModule,
    ]
})
export class CambioclaveComponent implements OnInit {
  authForm: UntypedFormGroup;
  submitted = false;
  returnUrl: string;
  hide = true;
  chide = true;
  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
  ) {}
  ngOnInit() {
    this.authForm = this.formBuilder.group({
      id: [this.authService.currentUserValue.id],
      password: ['',
        [Validators.required, Validators.minLength(6), MyValidators.validPassword]],
      cpassword: ['', Validators.required],
    }, {
      validators: MyValidators.matchPasswords
    });
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  get f() {
    return this.authForm.controls;
  }
  async onSubmit() {
    this.submitted = true;

    if (this.authForm.invalid) {
      return;
    } else {
      const res = await this.authService.post('/updatepassword', this.authForm.getRawValue(), false);
      if (res) {
        alert('El password ha sido actualizado exitosamente');
        this.router.navigate(['/admin/dashboard/main']);
      } else {
        alert('Se ha generado un error actualizando el password');
      }
    }
  }
}
