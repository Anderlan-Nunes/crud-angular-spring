import { inject } from '@angular/core';
import { ResolveFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Course } from '../models/course';
import { CoursesService } from '../services/courses.service';



export const courseResolver: ResolveFn<Course | Observable<Course>> = (
  route: ActivatedRouteSnapshot, 
  state: RouterStateSnapshot
) => {

  const courseService = inject(CoursesService); 
  // Verifica se existe o parâmetro 'id' na rota
  if (route.params && route.params['id']) { // if (route.params?.['id']) {
    return courseService.loadById(route.params['id']);
  }
  
  // Retorna um objeto vazio caso não tenha ID
  return of({ _id: '', name: '', category: '', lessons: [] }); // operador of faz retornar um observable
};