import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, ReactiveFormsModule, UntypedFormArray, Validators } from '@angular/forms';
import { Location } from '@angular/common';

import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBar} from '@angular/material/snack-bar';
import { MatIcon } from "@angular/material/icon";

import { HttpErrorResponse } from '@angular/common/http';
import { CoursesService } from '../../services/courses.service';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../models/course';
import { Lesson } from '../../models/lesson';


interface Category {
  value: string;
}

@Component({
  selector: 'app-course-form',
  imports: [MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatToolbarModule, MatSelectModule,
    ReactiveFormsModule, MatIcon],
  standalone: true,
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CourseFormComponent implements OnInit{

  private readonly _snackBar = inject(MatSnackBar);
  private readonly coursesService = inject(CoursesService);
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly _localtion = inject(Location); // Usando o Location para navegar de volta
  private readonly route = inject(ActivatedRoute);

  category: Category[] = [
    { value: 'Front-end' },
    { value: 'Back-end' }
  ];

  form!: FormGroup;

  constructor() {

  };

  ngOnInit() {
    const course: Course = this.route.snapshot.data['course']; // Obtendo o curso do resolver
   // console.log('Curso obtido do resolver:', course);

    this.form = this.formBuilder.group({
    _id: [course._id],
    name: [course.name, [Validators.required,
    Validators.minLength(5),
    Validators.maxLength(100)]],
    category: [course.category, Validators.required],
    lessons: this.formBuilder.array(this.retriveLessons(course))
  });
    console.log('ngOinit do course-form #course= ',course)
    console.log('ngOinit do Course-form #form=', this.form)
    console.log('ngOinit do Course-form #formvalue=', this.form.value)
  }

  private retriveLessons(course: Course) { // o array de licoes vem do curso, pode colocar aki diretamente o array de lesson mas como to trabalhando com a variavel curso eu vou usar aki
    const lessons = [];
    if (course?.lessons) {
      course.lessons?.forEach( lesson => lessons.push(this.createLesson(lesson)))
    } else {
      lessons.push(this.createLesson())
    }
    return lessons;
  }

  private createLesson(lesson: Lesson | null = null) {

    const lessonData = lesson ?? {id: '', name: '', youtubeUrl: ''};
    return this.formBuilder.group({
      id: [lessonData.id],
      name: [lessonData.name],
      youtubeUrl: [lessonData.youtubeUrl]
    });
  }

  getLessonsFormArray() {
    return (<UntypedFormArray>this.form.get('lessons')).controls
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
