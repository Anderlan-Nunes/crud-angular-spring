import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Location } from '@angular/common';

import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBar} from '@angular/material/snack-bar';

import { HttpErrorResponse } from '@angular/common/http';
import { CoursesService } from '../../services/courses.service';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../models/course';

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
  private readonly coursesService = inject(CoursesService);
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly _localtion = inject(Location); // Usando o Location para navegar de volta
  private readonly route = inject(ActivatedRoute);

  category: Category[] = [
    { value: 'Full-stack' },
    { value: 'Front-end' },
    { value: 'Back-end' },
  ];

  form = this.formBuilder.group({
    _id: [''],
    name: ['', [Validators.required,
    Validators.minLength(5),
    Validators.maxLength(100)]],
    category: ['', Validators.required]
  });

  constructor() { 

  };

  ngOnInit() {
    const course: Course = this.route.snapshot.data['course']; // Obtendo o curso do resolver
   // console.log('Curso obtido do resolver:', course);
   this.form.setValue({
      _id: course._id, 
      name: course.name,
      category: course.category
    });
  }
  onSubmit() {
    this.coursesService.save(this.form.value)
      .subscribe({
       // next: result => console.log('resultado: ', result),
        next: result => this.onSuccess(),
        error: error => this.onError(error),
        //complete: () => console.log('Operação concluída.') // (Opcional): o complete é chamado quando o observable termina, ou seja, quando o servidor responde // Remove o complete ou use apenas para limpeza/log
      });
  }

  onCancel() { 
    this._localtion.back();
  }

  private onSuccess() {
    this._snackBar.open('Curso salvo com sucesso!', '', {
      duration: 3000
    });
    this.onCancel();
  }

  private onError(error: HttpErrorResponse) {
    this._snackBar.open('Erro ao salvar curso!', 'Fechar', {
      duration: 7000
    });
  console.error('Erro ao salvar curso:', error);
  console.error('Status:', error.status);
  console.error('Detalhes:', error.error);
  console.log('Tentativa de salvamento falhou');
  }

  getErrorMessage(fildeName: string) {
    const field = this.form.get(fildeName);
    if (field?.hasError('required')) {
      return 'Você deve inserir um valor';
    }

    if (field?.hasError('minlength')) {
      const requiredLength : number = field.errors ? field.errors['minlength']['requiredLength'] : 5;
      return `O tamanho mínimo é de ${requiredLength} caracteres`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength : number = field.errors ? field.errors['maxlength']['requiredLength'] : 100;
      return `O tamanho máximo é de ${requiredLength} caracteres`;
    }
    return 'Campo inválido';
  }
}
