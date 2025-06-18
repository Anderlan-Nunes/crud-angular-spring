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

  loadById(id: string): Observable<Course> {
    return this.httpClient.get<Course>(`${this.API}/${id}`) //pega o id do curso que foi passado na rota
  }

  save(record: Partial<Course>) { // serve para salva e para update
    console.log('record->' + JSON.stringify(record));
    if (record._id) {
      //console.log('record._id->' + record._id);
      return this.update(record); // se o record tiver um id, significa que é uma atualização
    }
    //console.log('create');
    return this.create(record)
  }

  private create(record: Partial<Course>) { // o record é os dados que vem do formulario é o corpo do meu post
    return this.httpClient.post<Course>(this.API, record).pipe(
      first(), // finaliza a inscrição assim quando obter a primeira resposta que o servidor me enviar, não eh nescessário usar porque o angular ja termina a inscrição depois de receber a resposta do servidor mas é para garantir que a inscrição é finalizada.
    );
  }

  private update(record: Partial<Course>) {
    return this.httpClient.put<Course>(`${this.API}/${record._id}`, record).pipe(
      first(), // finaliza a inscrição assim quando obter a primeira resposta que o servidor me enviar, não eh nescessário usar porque o angular ja termina a inscrição depois de receber a resposta do servidor mas é para garantir que a inscrição é finalizada.
    );
  }

  remove(id: string){ //
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }
}

