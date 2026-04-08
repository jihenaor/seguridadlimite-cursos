import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';

@Component({
    selector: 'teacher-sidebar',
    templateUrl: './sidebar.component.html',
    standalone: true
})
export class SideBarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
