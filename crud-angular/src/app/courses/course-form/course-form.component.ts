import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';

interface Category {
  value: string;
  viewValue: string;
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

category: Category[] = [
    {value: '0', viewValue: 'Full-stack'},
    {value: '1', viewValue: 'Front-end'},
    {value: '2', viewValue: 'Back-end'},
  ];

  form: FormGroup;
  constructor(private readonly formBuilder: FormBuilder) { 
    this.form = this.formBuilder.group({
      name: [null],
      category: [null],
      // category: [this.category[0].value],
    });
  }

}
