import { AbstractControl, AsyncValidatorFn } from '@angular/forms';
export class MyValidators {

  static isPriceValid(control: AbstractControl) {
    const value = control.value;
    console.log(value);
    if (value > 10000) {
      return {price_invalid: true};
    }
    return null;
  }

  static validPassword(control: AbstractControl) {
    const value = control.value;
    if (value == null || String(value).trim() === '') {
      return null;
    }
    if (!containsNumber(String(value))) {
      return {invalid_password: true};
    }
    return null;
  }

  static matchPasswords(control: AbstractControl) {
    const password = control.get('password').value;
    const confirmPassword = control.get('cpassword').value;
    if (password !== confirmPassword) {
      return {match_password: true};
    }
    return null;
  }

  static validBirthDate(control: AbstractControl) {
    const value = control.value;
    if (!value) {
      return null; // Let required validator handle empty values
    }

    // Check if the format is YYYY-MM-DD
    const datePattern = /^\d{4}-\d{2}-\d{2}$/;
    if (!datePattern.test(value)) {
      return {invalid_date_format: true};
    }

    const parts = value.split('-');
    const year = parseInt(parts[0], 10);
    const month = parseInt(parts[1], 10);
    const day = parseInt(parts[2], 10);

    // Validate month (1-12)
    if (month < 1 || month > 12) {
      return {invalid_month: true};
    }

    // Validate day based on month and year
    const daysInMonth = new Date(year, month, 0).getDate();
    if (day < 1 || day > daysInMonth) {
      return {invalid_day: true};
    }

    // Validate that the date is not in the future
    const inputDate = new Date(year, month - 1, day);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    if (inputDate > today) {
      return {future_date: true};
    }

    // Validate reasonable birth date (not too old)
    const minDate = new Date();
    minDate.setFullYear(minDate.getFullYear() - 120);
    
    if (inputDate < minDate) {
      return {too_old: true};
    }

    return null;
  }

  static validDocumentType(control: AbstractControl) {
    const documenttype = control.get('tipodocumento').value;
    const documentnumber = control.get('numerodocumento').value;
    const gender = control.get('genero').value;

    const val = parseInt(documentnumber, 10);

    if (documenttype === 'CC') {
      if  (val >= 0 && val <= 19999999 ) {

        if (gender === 'F') {
          // Las cedulas entre 0 y 19999999 deben tener sexo Masculino"
          return {invalid_document1: true};
        }
      }
      if  (val >= 70000000 && val <= 99999999) {
        if (gender === 'F') {
          return {invalid_document2: true};
        }
      }

      if  (val >= 20000000 && val <= 69999999) {
        if (gender === 'M') {
          return {invalid_document3: true};
        }
      }

      if  ( documentnumber.length !== 3 &&
        documentnumber.length !== 4 &&
        documentnumber.length !== 5 &&
        documentnumber.length !== 6 &&
        documentnumber.length !== 7 &&
        documentnumber.length !== 8 &&
        documentnumber.length !== 10 &&
        documentnumber.length !== 11 &&
        documentnumber.length !== 12) {
        return {invalid_document4: true};
      }
    }
    return null;
    /*
          if (!Utilidades.isNumber(afiliacionmasiva.getNumdocumento())) {
	afiliacionmasiva.setError("T");
	afiliacionmasiva.setVtipodocumento("El número de documento " + afiliacionmasiva.getNumdocumento() + " no es válido");

	error = true;
      } else {
	if (tipoDocumento.equals("CC")) {
	  if  (new Long(afiliacionmasiva.getNumdocumento()) >= 0 && new Long(afiliacionmasiva.getNumdocumento()) <= 19999999 ) {
	    if (genero.equals("F")) {
	      afiliacionmasiva.setError("T");
	      afiliacionmasiva.setVtipodocumento("Las cedulas entre 0 y 19999999 deben tener sexo Masculino");

	      error = true;
	    }
	  }
	}

	if (tipoDocumento.equals("CC")) {
	}

	if (tipoDocumento.equals("TI")) {
	  if  (afiliacionmasiva.getNumdocumento().length() < 10 && afiliacionmasiva.getNumdocumento().length() > 11){
	    afiliacionmasiva.setError("T");
	    afiliacionmasiva.setVtipodocumento("El numero de digitos de la Tarjeta de Identidad debe ser entre 10 y 11");

	    error = true;
	  }
	}

	if (tipoDocumento.equals("CC")) {

	    error = true;
	  }
	}

	if (tipoDocumento.equals("PA")){
	  if (afiliacionmasiva.getNumdocumento().length() > 17){
	    afiliacionmasiva.setError("T");
	    afiliacionmasiva.setVtipodocumento("El numero de digitos del PASAPORTE debe ser menor a 17");

	    error = true;
	  }
	}

	if (tipoDocumento.equals("CE")){
	  if (afiliacionmasiva.getNumdocumento().length() < 6){
	    afiliacionmasiva.setError("T");
	    afiliacionmasiva.setVtipodocumento("El numero de digitos de la CEDULA EXTRANJERIA debe ser minimo 6");

	    error = true;
	  }
	}

	if (tipoDocumento.equals("PE")) {
	  if  (afiliacionmasiva.getNumdocumento().length() != 15
	      && afiliacionmasiva.getNumdocumento().length() != 14) {
	    afiliacionmasiva.setError("T");
	    afiliacionmasiva.setVtipodocumento("El numero de digitos del Permiso Especial de Permanencia debe ser de 14 o 15");

	    error = true;
	  }
	}
      }
    */
  }

  static validateExistDocument(): AsyncValidatorFn {
    return (control: AbstractControl) => {
      const value = control.value;
      let id;
      return fetch(`https://seguridadallimite.com:8080/seguridadallimite-1.0/api/trabajadornumerodocumento/${value}`)
      .then(x => x.json())
      .then(
        data => id = {existe: data.id ? true : false}
      );

      // return {invalid_document4: true};
    };
  }

}


function containsNumber(value: string) {
  return value.split('').find(v => isNumber(v)) !== undefined;
}

function isNumber(value: string) {
  return !isNaN(parseInt(value, 10));
}
