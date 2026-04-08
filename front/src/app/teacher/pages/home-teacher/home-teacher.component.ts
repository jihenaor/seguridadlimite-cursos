import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
    templateUrl: './home-teacher.component.html',
    imports: [RouterOutlet]
})
export class HomeTeacherComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    // No redirigir a ninguna ruta específica
  }

}
