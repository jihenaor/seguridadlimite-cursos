import { Trabajador } from '../models/trabajador.model';

/** Nombre legible a partir de los campos del trabajador (misma lógica que evaluación / quiz). */
export function nombreCompletoDesdeTrabajador(
  t: Pick<Trabajador, 'primernombre' | 'segundonombre' | 'primerapellido' | 'segundoapellido'> | null | undefined,
): string {
  if (!t) {
    return '';
  }
  return [t.primernombre, t.segundonombre, t.primerapellido, t.segundoapellido]
    .map((p) => (p ?? '').trim())
    .filter(Boolean)
    .join(' ')
    .trim();
}
