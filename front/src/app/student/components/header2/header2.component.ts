import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'shared-header',
    templateUrl: './header2.component.html',
    standalone: true
})
export class Header2Component implements OnInit {
  @Input()
  textHeader: string;

  constructor() { }

  ngOnInit(): void {
  }

}
