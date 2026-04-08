import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-block-header',
    imports: [RouterModule],
    template: `
    <div class="block-header">
      <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          <ul class="breadcrumb breadcrumb-style">
            <li class="breadcrumb-item bcrumb-1">
              <a routerLink="/admin/dashboard/main">
                <i class="fas fa-home"></i>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  `
})
export class BlockHeaderComponent {}