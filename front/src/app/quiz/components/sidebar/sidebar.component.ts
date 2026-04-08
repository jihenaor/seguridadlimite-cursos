import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { MessageService } from 'src/app/core/service/message.service';


@Component({
    selector: 'quiz-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss'],
    standalone: true,
})
export class SidebarComponent implements OnDestroy {
  isNavbarCollapsed = true;
  homePage: string = 'https://seguridadallimite.com';
  sidebarMessage: string = '';
  private messageSubscription: Subscription;

  constructor(private messageService: MessageService) {
    this.messageSubscription = this.messageService.message$.subscribe((message) => {
      this.sidebarMessage = message;
    });
  }

  ngOnDestroy() {
    this.messageSubscription.unsubscribe();
  }
}
