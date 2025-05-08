import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { catchError, Observable, of } from 'rxjs';

import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';

import { Course } from './models/course';
import { CoursesService } from './services/courses.service';
import { ErrorDialogComponent } from '../shared/components/error-dialog/error-dialog.component';
import { CategoryPipe } from "../shared/pipes/category.pipe";



@Component({
  selector: 'app-courses',
  imports: [MatTableModule, MatCardModule, MatToolbarModule, MatProgressSpinnerModule, MatIconModule,
    CommonModule, CategoryPipe],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CoursesComponent {

  displayedColumns: string[] = ['_id', 'name', 'category'];
  courses$: Observable<Course[]>
  readonly dialog = inject(MatDialog);

  constructor(private coursesService: CoursesService) {
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
