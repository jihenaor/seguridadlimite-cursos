import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { Header2Component } from '../../../student/components/header2/header2.component';

@Component({
    selector: 'home-quiz',
    templateUrl: './home-quiz.component.html',
    imports: [
        Header2Component,
        RouterOutlet
    ]
})
export class HomeQuizComponent {}
