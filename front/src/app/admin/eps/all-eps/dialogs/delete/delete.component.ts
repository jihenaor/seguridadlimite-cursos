import { Component, Inject } from '@angular/core';
import { EpsService } from '../../eps.service';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
@Component({
    selector: 'app-delete',
    templateUrl: './delete.component.html',
    styleUrls: ['./delete.component.sass'],
    imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatButtonModule, MatDialogClose]
})
export class DeleteDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public studentsService: EpsService
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
  confirmDelete(): void {
//    this.studentsService.deleteStudents(this.data.id);
  }
}
