import { Component, OnDestroy } from '@angular/core';
import { Event, Router, NavigationStart, NavigationEnd, RouterOutlet } from '@angular/router';
import { BehaviorSubject, Subscription } from 'rxjs';
import { LoadingService } from './core/service/loading.service';
import { LoadingSpinnerComponent } from './shared/components/loading-spinner/loading-spinner.component';
import { SideBarComponent } from './teacher/components/sidebar/sidebar.component';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    imports: [RouterOutlet,
        LoadingSpinnerComponent,
        CommonModule,

    ]
})
export class AppComponent implements OnDestroy {
  currentUrl: string;
  private routerSubscription: Subscription;
  private loadingSubscription: Subscription;

  presentTheme$ = new BehaviorSubject<string>('theme-light');


  isDarkMode: boolean;

  isDarkEnable = false;


  constructor(
    private router: Router,
    private loadingService: LoadingService
    ) {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
      this.presentTheme$.next(savedTheme);
    }

    this.routerSubscription = this.router.events.subscribe((routerEvent: Event) => {
      if (routerEvent instanceof NavigationStart) {
        this.currentUrl = routerEvent.url.substring(
          routerEvent.url.lastIndexOf('/') + 1
        );
      }


      window.scrollTo(0, 0);

      const savedTheme = localStorage.getItem('theme');

    });

    this.loadingSubscription = this.loadingService.loading$.subscribe();
  }

  ngOnDestroy() {
    this.routerSubscription.unsubscribe();
    this.loadingSubscription.unsubscribe();
  }

  changeTheme() {
    this.presentTheme$.value === 'theme-light'
      ? this.presentTheme$.next('theme-dark')
      : this.presentTheme$.next('theme-light');
    localStorage.setItem('theme', this.presentTheme$.value);
    this.isDarkEnable = !this.isDarkEnable;
  }
}
