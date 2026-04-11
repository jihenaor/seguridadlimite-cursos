import { HttpErrorResponse } from '@angular/common/http';

const MENSAJE_POR_ESTADO: Record<number, string> = {
  0: 'No se pudo conectar con el servidor. Compruebe su conexión a internet.',
  400: 'La solicitud no es válida. Revise los datos e intente de nuevo.',
  401: 'Sesión no válida o expirada. Inicie sesión de nuevo.',
  403: 'No tiene permisos para realizar esta acción.',
  404: 'El recurso solicitado no existe o no está disponible.',
  409: 'No se pudo completar la operación por un conflicto con los datos actuales.',
  413: 'El archivo o los datos enviados son demasiado grandes.',
  422: 'Los datos enviados no pasaron la validación.',
  429: 'Demasiadas solicitudes en poco tiempo. Espere un momento e intente de nuevo.',
  500: 'Ocurrió un error en el servidor. Intente más tarde o contacte al administrador.',
  502: 'El servidor no está disponible temporalmente. Intente más tarde.',
  503: 'El servicio no está disponible en este momento. Intente más tarde.',
  504: 'El servidor tardó demasiado en responder. Intente de nuevo.',
};

/** Texto que Angular pone en `HttpErrorResponse.message` (poco útil para el usuario). */
function esMensajeTecnicoAngular(msg: string | undefined): boolean {
  return !!msg && msg.includes('Http failure response');
}

function esMensajeTecnicoBackend(msg: string): boolean {
  const m = msg.toLowerCase();
  return (
    m.includes('java.') ||
    m.includes('exception') ||
    m.includes('stacktrace') ||
    m.includes('org.springframework') ||
    m.length > 400
  );
}

/**
 * Mensaje breve y legible para mostrar en snackbar / UI.
 * Evita cadenas del tipo "Http failure response for http://...".
 */
export function mensajeErrorHttpAmigable(error: HttpErrorResponse, maxLength = 280): string {
  const body = error.error;

  if (typeof body === 'string') {
    const t = body.trim();
    if (t.length > 0 && !t.startsWith('<') && !esMensajeTecnicoBackend(t)) {
      return truncar(t, maxLength);
    }
  }

  if (body && typeof body === 'object') {
    const candidatos = ['message', 'detail', 'error', 'title'] as const;
    for (const k of candidatos) {
      const v = (body as Record<string, unknown>)[k];
      if (typeof v === 'string' && v.trim().length > 0 && !esMensajeTecnicoBackend(v)) {
        return truncar(v.trim(), maxLength);
      }
    }
  }

  if (MENSAJE_POR_ESTADO[error.status]) {
    return MENSAJE_POR_ESTADO[error.status];
  }

  if (esMensajeTecnicoAngular(error.message)) {
    return (
      MENSAJE_POR_ESTADO[error.status] ||
      'No se pudo completar la operación. Si el problema continúa, contacte al administrador.'
    );
  }

  if (error.statusText && error.statusText !== 'Unknown Error') {
    return truncar(`Error (${error.status}): ${error.statusText}`, maxLength);
  }

  return 'No se pudo completar la operación.';
}

function truncar(text: string, max: number): string {
  if (text.length <= max) {
    return text;
  }
  return `${text.slice(0, max - 1)}…`;
}
