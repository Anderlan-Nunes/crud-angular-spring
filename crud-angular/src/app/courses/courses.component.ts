import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { catchError, Observable, of } from 'rxjs';

import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';

import { Course } from './models/course';
import { CoursesService } from './services/courses.service';
import { ErrorDialogComponent } from '../shared/components/error-dialog/error-dialog.component';
import { CoursesListComponent } from "./courses-list/courses-list.component";



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


}
