import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Pregunta } from '../../../core/models/pregunta.model';

/** Bloque de preguntas bajo un mismo énfasis (vista agrupada). */
export interface PreguntasEnfasisBloque {
  idEnfasis: number | null;
  nombre: string;
  preguntas: Pregunta[];
}

@Injectable()
export class PreguntasService {
  private readonly source = signal<Pregunta[]>([]);

  /** Texto de búsqueda (agrupadores y texto de pregunta). */
  readonly textFilter = signal('');

  /** Ids de énfasis seleccionados; vacío = no filtrar por énfasis. */
  readonly selectedEnfasisIds = signal<number[]>([]);

  /** Si es true, la UI puede mostrar preguntas agrupadas por énfasis (ruta por grupo). */
  readonly groupByEnfasis = signal(false);

  /** Opciones de filtro derivadas de las preguntas cargadas (ruta por grupo). */
  readonly enfasisFilterOptions = computed(() => {
    const map = new Map<number, string>();
    for (const p of this.source()) {
      if (p.enfasis?.id != null) {
        map.set(p.enfasis.id, p.enfasis.nombre ?? '');
      }
    }
    return [...map.entries()]
      .map(([id, nombre]) => ({ id, nombre }))
      .sort((a, b) => a.nombre.localeCompare(b.nombre, 'es'));
  });

  readonly preguntas = computed(() => {
    const all = this.source();
    let rows = [...all];
    const text = this.textFilter().trim().toLowerCase();
    if (text) {
      rows = rows.filter((p) => {
        const a1 = (p.agrupador1 || '').toLowerCase();
        const a2 = (p.agrupador2 || '').toLowerCase();
        const q = (p.pregunta || '').toLowerCase();
        return a1.includes(text) || a2.includes(text) || q.includes(text);
      });
    }
    const enfSel = this.selectedEnfasisIds();
    if (enfSel.length > 0) {
      const set = new Set(enfSel);
      rows = rows.filter((p) => p.enfasis?.id != null && set.has(p.enfasis.id));
    }
    return rows;
  });

  /**
   * Lista filtrada partida por énfasis (nombre ordenado), cada bloque con preguntas por `orden`.
   */
  readonly preguntasPorEnfasis = computed((): PreguntasEnfasisBloque[] => {
    const rows = this.preguntas();
    const map = new Map<string, PreguntasEnfasisBloque>();

    for (const p of rows) {
      const id = p.enfasis?.id;
      const mapKey = id != null ? `id:${id}` : 'sin';
      const nombre = (p.enfasis?.nombre ?? '').trim() || 'Sin énfasis';
      if (!map.has(mapKey)) {
        map.set(mapKey, { idEnfasis: id ?? null, nombre, preguntas: [] });
      }
      map.get(mapKey)!.preguntas.push(p);
    }
    const bloques = [...map.values()].map((b) => ({
      ...b,
      preguntas: [...b.preguntas].sort(
        (a, c) => (a.orden ?? 0) - (c.orden ?? 0)
      ),
    }));
    return bloques.sort((a, b) =>
      a.nombre.localeCompare(b.nombre, 'es', { sensitivity: 'base' })
    );
  });

  constructor(private httpClient: HttpClient) {}

  getPreguntasNivelTipoEvaluacion(idnivel: string, tipoevaluacion: string): void {
    const nameService = '/preguntarniveltipoevaluacion/' + idnivel + '/' + tipoevaluacion;
    this.httpClient
      .get<Pregunta[]>(`${environment.apiUrl}` + nameService)
      .subscribe({ next: (data) => this.applyLoadedData(data) });
  }

  getPreguntasGrupo(idgrupo: string): void {
    const nameService = '/pregunta/' + idgrupo + '/grupo';
    this.httpClient
      .get<Pregunta[]>(`${environment.apiUrl}` + nameService)
      .subscribe({ next: (data) => this.applyLoadedData(data) });
  }

  private applyLoadedData(data: Pregunta[] | null | undefined): void {
    this.source.set(data ?? []);
    this.textFilter.set('');
    this.selectedEnfasisIds.set([]);
  }

  setTextFilter(value: string): void {
    this.textFilter.set(value);
  }

  clearEnfasisFilter(): void {
    this.selectedEnfasisIds.set([]);
  }

  toggleEnfasisId(id: number): void {
    const cur = this.selectedEnfasisIds();
    if (cur.includes(id)) {
      this.selectedEnfasisIds.set(cur.filter((x) => x !== id));
    } else {
      this.selectedEnfasisIds.set([...cur, id]);
    }
  }

  setGroupByEnfasis(value: boolean): void {
    this.groupByEnfasis.set(value);
  }

  /** Compatibilidad con la plantilla anterior que llamaba `filter(...)`. */
  filter(term: string): void {
    this.setTextFilter(term);
  }
}
