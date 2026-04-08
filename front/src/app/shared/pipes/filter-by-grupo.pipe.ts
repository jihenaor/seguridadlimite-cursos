import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByGrupo',
  standalone: true
})
export class FilterByGrupoPipe implements PipeTransform {
  transform(items: any[], grupoId: number): any[] {
    if (!items) return [];
    return items.filter(item => item.idGrupo === grupoId);
  }
}
