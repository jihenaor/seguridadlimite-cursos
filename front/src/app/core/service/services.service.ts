import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';


@Injectable()
export class ServicesService {

  constructor() {

  }

  private authHeaders(): Record<string, string> {
    const token = sessionStorage.getItem('token');
    return {
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    };
  }

  async executeFetch(url: string) {
    try {
      const response = await fetch(`${environment.apiUrl}${url}`, {
        method: 'GET',
        headers: this.authHeaders(),
      });

      switch (response.status) {
        case 400:
        case 401:
        case 404:
        case 500:
          const errorBody = await response.json();
          alert(errorBody.message || 'Error en el servidor');
          return undefined;
        case 200:
          const data = await response.json();
          return data;
        default:
          alert('Error consultando datos en WS ' + url + ' Error: ' + response.status);
          console.log('Error ejecutando fetch. Url ' + url + ' no existe');
          return undefined;
      }
    } catch (error) {
      console.error('Error in executeFetch:', error);
      console.log('URL:', url);
      let errorMessage = 'Error inesperado en el servidor';

      if (error instanceof Error) {
        errorMessage = error.message;
      } else if (error.json) {
        try {
          const errorBody = await error.json();
          errorMessage = errorBody.message;
        } catch {
          // Si no se puede parsear el JSON, usamos el mensaje por defecto
        }
      }

      alert('Error in executeFetch: ' + errorMessage);
      return undefined;
    }
  }

  async executeFetchHttps(url: string) {
    try {
      /*
      const response = await fetch(`https://seguridadallimite.com:8 0 8 0/seguridadallimite-1.0/api/${url}`, {
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': '*',
          'Access-Control-Allow-Methods': '*',
        },
      });
*/

      const response = await fetch(`https://seguridadallimite.com:8 0 8 0/seguridadallimite-1.0/api/${url}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
          },
      });

      switch (response.status) {
        case 401:
          alert('La sesion de trabajo no es válida debe autenticar nuevamente ');
          break;
        case 404:
          alert('Error consultando datos en WS ' + url);
          console.log('Error ejecutando fetch. Url ' + url + ' no existe');
          return undefined;
        case 200:
          const data = await response.json();

          return data;
        default:
            alert('Error consultando datos en WS ' + url
                  + ' Error: ' + response.status);
            console.log('Error ejecutando fetch. Url ' + url + ' no existe');
            return undefined;
      }
    } catch (error) {
      console.log('Error in executeFetch. ', error);
      console.log(url);
      alert('Error in executeFetch. WS: '
      + ' Error: ' + error.message);
//      this.router.navigate([ '/pages/login' ]);
    }
  }

  async post(nombreservicio: string, entidad: any, method: string = 'POST') {
    try {
      const url = `${environment.apiUrl}${nombreservicio}`;
      const datos = JSON.stringify(entidad);

      const response = await fetch(url, {
        method,
        body: datos,
        headers: this.authHeaders(),
      });

      const data = await response.json();

      if (!response.ok) {
        throw {
          message: data.message || 'Error en la operación',
          status: response.status,
          statusText: response.statusText,
          data
        };
      }

      return data;
    } catch (error) {
      if (error instanceof SyntaxError) {
        throw {
          message: 'Error al procesar la respuesta del servidor',
          status: 500
        };
      }
      throw error;
    }
  }

  async delete(servicio: string) {
    const url = `${environment.apiUrl}${servicio}`;
    let response;
    try {
      response = await fetch(url,
        {
          method:'DELETE',
          headers: this.authHeaders(),
        });
    } catch (error) {
      throw new Error('Se genero un error: ' + error);
    } finally {

    }

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      throw {
        json: await response.json(),
        status: response.status,
        statusText: response.statusText
      };
//        throw new Error('Se genero un error: ' + response.statusText);
    }
  }

  async getDocumentos(idnivel, idgrupo) {
    const documentos = await this.executeFetch('/documentos');
    let _documentos = [];
    if (!idnivel) {
      return documentos;
    }
    documentos.forEach(documento => {

      switch (idnivel) {
        case 1: // Avanzado
          if (documento.id == 1 || documento.id == 2 || documento.id == 3 || documento.id == 4) {
            _documentos.push(documento);
          }
          break;
        case 2: // Coordinador
          if (documento.id == 1 || documento.id == 2 || documento.id == 3 || documento.id == 4 || documento.id == 5 || documento.id == 6) {
            _documentos.push(documento);
          }
          break;
        case 3: // Basico operativo
          if (documento.id == 1 || documento.id == 2 || documento.id == 3 || documento.id == 4) {
            _documentos.push(documento);
          }
          break;
        case 4: // reentrenamiento
          if (documento.id == 1 || documento.id == 2 || documento.id == 3 || documento.id == 4 || documento.id == 5) {
            _documentos.push(documento);
          }
          break;
        case 5: // Administrativo
          if (documento.id == 1 || documento.id == 2 || documento.id == 3) {
            _documentos.push(documento);
          }
          break;
      }
    });

    return _documentos;
  }
}
