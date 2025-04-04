import { Component } from '@angular/core';

import {MatCardModule} from '@angular/material/card';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';

import { Course } from './models/course';
import { CoursesService } from './services/courses.service';

@Component({
  selector: 'app-courses',
  imports: [MatTableModule, MatCardModule, MatToolbarModule],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss'
})
export class CoursesComponent {

  displayedColumns: string[] = ['name', 'category'];
  courses: Course[] = [];

  constructor(private coursesService: CoursesService) {

  }
  ngOnInit() {
    this.courses = this.coursesService.listCourses();
  }

}
