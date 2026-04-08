import { Component, OnInit } from '@angular/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RouterLink } from '@angular/router';
@Component({
    selector: 'app-about-professor',
    templateUrl: './about-professor.component.html',
    styleUrls: ['./about-professor.component.sass'],
    imports: [RouterLink, MatFormFieldModule, MatInputModule, MatButtonModule, MatCheckboxModule]
})
export class AboutProfessorComponent implements OnInit {
  constructor() {}
  ngOnInit(): void {}
}
