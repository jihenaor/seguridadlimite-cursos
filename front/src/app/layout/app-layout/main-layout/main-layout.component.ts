import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RightSidebarComponent } from '../../right-sidebar/right-sidebar.component';
import { SidebarComponent } from '../../sidebar/sidebar.component';
import { HeaderComponent } from '../../header/header.component';

@Component({
    selector: 'app-main-layout',
    templateUrl: './main-layout.component.html',
    styleUrls: [],
    imports: [
        HeaderComponent,
        SidebarComponent,
        RightSidebarComponent,
        RouterOutlet,
    ]
})
export class MainLayoutComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
