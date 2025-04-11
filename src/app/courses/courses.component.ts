import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { catchError, Observable, of } from 'rxjs';

import {MatCardModule} from '@angular/material/card';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

import { Course } from './models/course';
import { CoursesService } from './services/courses.service';



@Component({
  selector: 'app-courses',
  imports: [MatTableModule, MatCardModule, MatToolbarModule, MatProgressSpinnerModule,
    CommonModule
  ],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss'
})
export class CoursesComponent {

  displayedColumns: string[] = ['name', 'category'];
  courses$: Observable<Course[]>

  constructor(private coursesService: CoursesService) {
    this.courses$ = this.coursesService.listCourses()

  }
  ngOnInit() {
    
  }

}
