import { ChangeDetectionStrategy, Component, inject, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';

import { CategoryPipe } from '../../shared/pipes/category.pipe';
import { Course } from '../models/course';

@Component({
  selector: 'app-courses-list',
  imports: [MatButtonModule, MatIconModule, MatTableModule, CategoryPipe],
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  
})
export class CoursesListComponent {

  @Input() courses: Course[] = [] // 1
  displayedColumns: string[] = ['_id', 'name', 'category', 'actions'];

  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  onAdd() {
  this.router.navigate(['new'], { relativeTo: this.route }); // relativeTo: this.route opcao extra que pega a rota que eu estou e agrega esse /new faz isso para nao precisar usar o courses/new. caso um dia precisasse mudar a rota, eu só mudaria aqui e não em todos os lugares que usei o /courses/new
} ; 

}
 
// 1 @Input()
//Dados do curso passados para o componente da lista usando `@Input()`:** O `CursosComponent` agora passa a lista de cursos para o `CursosListaComponent` através do data binding.