import { Component, inject, ChangeDetectionStrategy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { catchError, Observable, of, tap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

import { Course } from '../../models/course';
import { CoursesService } from '../../services/courses.service';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { CoursesListComponent } from "../../components/courses-list/courses-list.component";
import { MatSnackBar } from '@angular/material/snack-bar';
import { error } from 'console';
import { ConfirmationDialogComponent } from '../../../shared/confirmation-dialog/confirmation-dialog.component';
import { CoursePage } from '../../models/course-page';


const DEFAULT_PAGE_EVENT: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 };
@Component({
  selector: 'app-courses',
  imports: [MatCardModule, MatProgressSpinnerModule, MatToolbarModule,
    CommonModule, CoursesListComponent, MatPaginator],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss',
  changeDetection: ChangeDetectionStrategy.Default
})
export class CoursesComponent {

  courses$: Observable<CoursePage> | null = null;
  readonly dialog = inject(MatDialog);
  readonly route = inject(ActivatedRoute);
  readonly router = inject(Router);
  private readonly _snackBar = inject(MatSnackBar);
  cdr = inject(ChangeDetectorRef)

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private readonly coursesService: CoursesService) {
    this.loadCourses();
  }

  loadCourses(pageEvent: PageEvent = DEFAULT_PAGE_EVENT) {
    this.courses$ = this.coursesService.listCourses(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        // tap - pega o pageIndex e atualiza com o que eu estou passando aki pageEvent.pageIndex. "Executa efeitos colaterais (ex: atualizar variáveis ou logs) sem modificar o fluxo de dados principal."
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }), 
        catchError(error => {
          this.onError('Erro ao carregar cursos. Tente mais tarde!');
          console.log(error);
          return of({courses: [], totalElements: 0, totalPages: 0});
        })
      )
      this.cdr.markForCheck(); // <- força a verificação de mudança
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
// nenhuma chamada vai ser feita para o backend precisa eh se inscrever no observable para que a chamada sja executada
  onRemove(course: Course) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: `Tem certeza que deseja remover o curso: ${course.name}?`,
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.remove(course._id).subscribe({
          next: () => {
            this.loadCourses();
            this._snackBar.open('Curso removido com sucesso!', 'X', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            });
          },
          error: () => this.onError('Erro ao tentar remover curso!')
        })
      }
    });
  }
}


