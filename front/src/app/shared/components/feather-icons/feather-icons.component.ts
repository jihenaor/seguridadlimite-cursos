import { Component, OnInit, Input } from "@angular/core";
import { FeatherModule } from "angular-feather";

@Component({
    selector: "app-feather-icons",
    templateUrl: "./feather-icons.component.html",
    styleUrls: ["./feather-icons.component.sass"],
    imports: [FeatherModule]
})
export class FeatherIconsComponent implements OnInit {
  @Input("icon") public icon;
  @Input("class") public class;
  constructor() {}

  ngOnInit(): void {}
}
