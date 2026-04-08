import { Component, ElementRef, HostListener, Input, Output, EventEmitter } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
    selector: 'app-file-upload-base64',
    templateUrl: './file-upload-base64.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: FileUploadBase64Component,
            multi: true,
        },
    ],
    styleUrls: ['./file-upload-base64.component.scss'],
    standalone: true,
})
export class FileUploadBase64Component implements ControlValueAccessor {
  @Input() progress;
  onChange: Function;
  @Output() base64ValueChange = new EventEmitter<string | null>(); // Emisor de eventos para el valor base64Value

  public base64Value: string | null = null; // Variable que almacenará el contenido Base64
  public fileName: string | null = null; // Variable que almacenará el nombre del archivo seleccionado
  public fileExtension: string | null = null; // Variable que almacenará la extensión del archivo seleccionado

  @HostListener('change', ['$event.target.files']) emitFiles(event: FileList) {
    const file = event && event.item(0);
    this.readFileAsBase64(file); // Lee el contenido del archivo como Base64
  }

  constructor(private host: ElementRef<HTMLInputElement>) {}

  readFileAsBase64(file: File | null) {
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        this.base64Value = event.target?.result as string; // Asigna la cadena Base64 al valor
        this.fileName = file.name; // Asigna el nombre del archivo seleccionado

        // Extrae la extensión del archivo
        const fileNameParts = file.name.split('.');
        this.fileExtension = fileNameParts[fileNameParts.length - 1].toLowerCase(); // Almacena la extensión en minúsculas
        this.emitBase64Value();
//        this.onChange(this.base64Value); // Llama a la función onChange con el valor Base64
      };
      reader.readAsDataURL(file);
    } else {
      this.base64Value = null; // Si no se selecciona ningún archivo, reinicia el valor
      this.fileName = null; // Reinicia el nombre del archivo seleccionado
//      this.onChange(this.base64Value); // Llama a la función onChange con valor nulo
    }
  }

  writeValue(value: null) {
    // clear file input
    this.host.nativeElement.value = '';
    this.base64Value = null; // Cuando se establece el valor del formulario, reinicia la cadena Base64
    this.fileName = null; // Reinicia el nombre del archivo seleccionado
  }

  registerOnChange(fn: Function) {
    this.onChange = fn;
  }

  registerOnTouched(fn: Function) {}

  // Nueva función para capturar el archivo seleccionado
  onFileSelected(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    const files = inputElement.files;
    if (files && files.length > 0) {
      this.readFileAsBase64(files[0]);
    }
  }

   emitBase64Value() {
    this.base64ValueChange.emit(this.base64Value);
  }
}
