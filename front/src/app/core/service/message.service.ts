import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private messageSource = new Subject<string>();
  message$ = this.messageSource.asObservable();

  updateMessage(message: string) {
    this.messageSource.next(message);
  }
}
