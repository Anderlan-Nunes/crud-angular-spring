import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { catchError, Observable, of } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';

import { Course } from '../../models/course';
import { CoursesService } from '../../services/courses.service';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { CoursesListComponent } from "../../components/courses-list/courses-list.component";



@Component({
  selector: 'app-courses',
  imports: [MatCardModule,MatProgressSpinnerModule, MatToolbarModule,
    CommonModule, CoursesListComponent],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CoursesComponent {

  courses$: Observable<Course[]>
  readonly dialog = inject(MatDialog);
  readonly route = inject(ActivatedRoute);
  readonly router = inject(Router);


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

  onAddCourse(){
    this.router.navigate(['new'], { relativeTo: this.route }); // relativeTo: this.route opcao extra que pega a rota que eu estou e agrega esse /new faz isso para nao precisar usar o courses/new. caso um dia precisasse mudar a rota, eu só mudaria aqui e não em todos os lugares que usei o /courses/new
  }

  onEditCourse(course: Course) { // o evento pode ser qualquer coisa mas espero uma variavel do tipo Course, Ai faz o que quer com essa variavel.
    this.router.navigate(['edit', course._id], { relativeTo: this.route });
  }

}
