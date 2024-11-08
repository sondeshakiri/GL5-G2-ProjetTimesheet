import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DepartementComponent } from './departement/departement.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,DepartementComponent,],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'my-angular-app';
}
