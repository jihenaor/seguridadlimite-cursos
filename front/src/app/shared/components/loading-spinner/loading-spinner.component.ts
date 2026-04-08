import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoadingService } from './../../../core/service/loading.service';
import { NgIf, AsyncPipe } from '@angular/common';

@Component({
    selector: 'app-loading-spinner',
    templateUrl: './loading-spinner.component.html',
    styleUrls: ['./loading-spinner.component.css'],
    imports: [NgIf, AsyncPipe]
})
export class LoadingSpinnerComponent implements OnDestroy {
  loading = false;
  private subscription: Subscription;

  constructor(public loadingService: LoadingService) {
    this.subscription = this.loadingService.loading$.subscribe((loading) => {

      this.loading = loading;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
