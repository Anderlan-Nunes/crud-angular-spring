import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Course } from '../models/course';
import { delay, first, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = './assets/courdses.json';

  constructor(private httpClient : HttpClient) { }

  listCourses() : Observable<Course[]> {
    return this.httpClient.get<Course[]>(this.API).pipe( 
      first(), // finaliza a inscrição assim quando obter a primeira resposta que o servidor me enviar, (usa quando nao eh um websocket)
      delay(5000),
      tap(courses => console.log(courses)) // tap é um operador que permite executar uma função com o resultado do observable, sem alterar o resultado do observable usa-se para fazer debug
    );  
  }
}

