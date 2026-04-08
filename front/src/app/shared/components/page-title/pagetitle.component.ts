import { Component, OnInit, Input } from "@angular/core";

@Component({
    selector: "app-pagetitle",
    templateUrl: "./pagetitle.component.html",
    styleUrls: ["./pagetitle.component.sass"],
    standalone: true,
})
export class PagetitleComponent implements OnInit {
  @Input() title: string;
  @Input() items: any[];
  @Input() active_item: string;

  constructor() {}

  ngOnInit(): void {}
}
