import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { catchError, Observable, of } from 'rxjs';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';

import { Course } from './models/course';
import { CoursesService } from './services/courses.service';
import { ErrorDialogComponent } from '../shared/components/error-dialog/error-dialog.component';
import { CategoryPipe } from "../shared/pipes/category.pipe";
import { ActivatedRoute, Router } from '@angular/router';



@Component({
  selector: 'app-courses',
  imports: [MatButtonModule, MatCardModule, MatIconModule, MatProgressSpinnerModule,MatTableModule, MatToolbarModule,
    CommonModule, CategoryPipe],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CoursesComponent {


  displayedColumns: string[] = ['_id', 'name', 'category', 'actions'];
  courses$: Observable<Course[]>
  readonly dialog = inject(MatDialog);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  constructor(private readonly coursesService: CoursesService) {
    this.courses$ = this.coursesService.listCourses()
    .pipe(
      catchError(error => {
        this.onError('Erro ao carregar cursos. Tente mais tarde!');
        console.log(error);
        return of([]);
      })
    )
  }
  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    })
  }

onAdd() {
  this.router.navigate(['new'], { relativeTo: this.route }); // relativeTo: this.route opcao extra que pega a rota que eu estou e agrega esse /new faz isso para nao precisar usar o courses/new. caso um dia precisasse mudar a rota, eu só mudaria aqui e não em todos os lugares que usei o /courses/new
} ; 
}
