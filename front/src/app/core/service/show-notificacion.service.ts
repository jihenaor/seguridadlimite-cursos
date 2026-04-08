import { Injectable } from '@angular/core';
import { NotificationColor } from '../enumerators/notification-color.enum';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})

export class ShowNotificacionService {

  constructor(private snackBar: MatSnackBar) { }

  display(colorName: NotificationColor, text, placementFrom, placementAlign) {
    this.snackBar.open(text, '', {
      duration: 6000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }


  displayError(text: string) {
    this.snackBar.open(text, 'cerrar', {
      panelClass: NotificationColor.Error,
    });
  }

  displayWarning( text: string) {
    this.snackBar.open(text, '', {
      duration: 6000,
      verticalPosition: 'bottom',
      horizontalPosition: 'center',
      panelClass: [NotificationColor.Warning]
    });
  }

  displaySuccess( text: string) {
    this.snackBar.open(text, '', {
      duration: 2000,
      verticalPosition: 'bottom',
      horizontalPosition: 'center',
      panelClass: [NotificationColor.success]
    });
  }
}
