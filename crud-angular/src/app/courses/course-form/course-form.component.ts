import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBar} from '@angular/material/snack-bar';

import { CoursesService } from '../services/courses.service';
import { HttpErrorResponse } from '@angular/common/http';

interface Category {
  value: string;
}

@Component({
  selector: 'app-course-form',
  imports: [MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatToolbarModule, MatSelectModule,
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CourseFormComponent {

private readonly _snackBar = inject(MatSnackBar);

category: Category[] = [
    {value: 'Full-stack'},
    {value: 'Front-end'},
    {value: 'Back-end'},
  ];

  form: FormGroup;
  constructor(private readonly formBuilder: FormBuilder,
    private readonly coursesService: CoursesService
  ) { 
    this.form = this.formBuilder.group({
      name: [null],
      category: [null],
      // category: [this.category[0].value],
    });
  }

  onSubmit() {
    this.coursesService.save(this.form.value)
      .subscribe({
        next: result => console.log('resultado: ', result),
        error: error => this.onError(error),
        complete: () => console.log('Operação concluída.') // (Opcional): o complete é chamado quando o observable termina, ou seja, quando o servidor responde
      });
  }

  onCancel() {
    this.form.reset();
  }

  private onError(error: HttpErrorResponse) {
    this._snackBar.open('Erro ao salvar curso!', 'Fechar', {
      duration: 5000
    });
  console.error('Erro ao salvar curso:', error);
  console.error('Status:', error.status);
  console.error('Detalhes:', error.error);
  console.log('Tentativa de salvamento falhou');
  }
}
