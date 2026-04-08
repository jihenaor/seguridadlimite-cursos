import { NgModule } from '@angular/core';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { PagetitleComponent } from './page-title/pagetitle.component';
import { SharedModule } from '../shared.module';
import { FileUploadBase64Component } from './file-upload-base64/file-upload-base64.component';

@NgModule({
    imports: [SharedModule, FileUploadComponent,
        PagetitleComponent,
        FileUploadBase64Component],
    exports: [
        FileUploadComponent,
        PagetitleComponent,
        FileUploadBase64Component
    ],
})
export class ComponentsModule {}
