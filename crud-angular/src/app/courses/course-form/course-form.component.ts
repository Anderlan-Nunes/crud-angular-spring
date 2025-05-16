import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';

@Component({
  selector: 'app-course-form',
  imports: [MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatToolbarModule,
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CourseFormComponent {

  form: FormGroup;
  constructor(private readonly formBuilder: FormBuilder) { 
    this.form = this.formBuilder.group({
      name: [null],
      category: [null],

    });
  }

}
