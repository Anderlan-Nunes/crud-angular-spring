import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Course } from '../models/course';
import { delay, first, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses';

  constructor(private readonly httpClient : HttpClient) { }

  listCourses() : Observable<Course[]> {
    return this.httpClient.get<Course[]>(this.API).pipe( 
      first(), // finaliza a inscrição assim quando obter a primeira resposta que o servidor me enviar, (usa quando nao eh um websocket)
      // delay(5000),
      tap(courses => console.log(courses)) // tap é um operador que permite executar uma função com o resultado do observable, sem alterar o resultado do observable usa-se para fazer debug
    );  
  }

  save(record: Partial<Course>) { // o record é os dados que vem do formulario é o corpo do meu post
    return this.httpClient.post<Course>(this.API, record).pipe(
      first(), // finaliza a inscrição assim quando obter a primeira resposta que o servidor me enviar, não eh nescessário usar porque o angular ja termina a inscrição depois de receber a resposta do servidor mas é para garantir que a inscrição é finalizada.
    );
  }
}

