import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';

import { CategoryPipe } from '../../../shared/pipes/category.pipe';
import { Course } from '../../models/course';

@Component({
  selector: 'app-courses-list',
  imports: [MatButtonModule, MatIconModule, MatTableModule, CategoryPipe],
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  
})
export class CoursesListComponent {

  @Input() courses: Course[] = [] // 1
  @Output() add = new EventEmitter(false);  // 2
  @Output() edit = new EventEmitter(false);

  readonly displayedColumns: string[] = ['_id', 'name', 'category', 'actions'];


  onAdd() { // 3
    // Método chamado quando o usuário clica no botão de adicionar curso
    this.add.emit(true);
  }

  onEdit(course: Course) {
    this.edit.emit(course); // eu poderia passar só o identificador mas vou passara variavei inteira e quem que for escultar evento decide o que fazer com essa informação
  }

}


 
// 1 @Input()
//Dados do curso passados para o componente da lista usando `@Input()`:** O `CursosComponent` agora passa a lista de cursos para o `CursosListaComponent` através do data binding.

/**
 * - `CursosListComponent` foi refatorado para ser um componente de apresentação, focado na exibição de dados e emissão de eventos.
 - A comunicação entre smart e dumb components é feita através de @Input() e @Output() (EventEmitter). 

 */

//2 @Output() add = new EventEmitter(false);

// - **`@Output()`**: Torna a propriedade `add` um evento que o componente pode emitir para o componente pai.
// - **`add`**: É o nome do evento. O componente pai pode escutar esse evento usando `(add)="algumaFuncao()"` no template.
// - **`new EventEmitter(false)`**: Cria um novo emissor de eventos. O parâmetro `false` indica que o EventEmitter não é assíncrono (padrão).

// ### `onAdd()`

// - É um método da classe.
// - Quando chamado (por exemplo, ao clicar em um botão), executa `this.add.emit(true);`.
// - **`this.add.emit(true)`**: Dispara o evento `add` e envia o valor `true` para o componente pai.

// ---

// #### Exemplo de uso no template pai:

// ```html
// <app-courses-list [courses]="lista" (add)="abrirFormulario()"></app-courses-list>
// ```

// Quando `onAdd()` for chamado no componente filho, o método `abrirFormulario()` do componente pai será executado.

// ---

// ### Resumindo

// - **`add`**: Evento customizado que o componente filho pode emitir.
// - **`onAdd()`**: Método que dispara esse evento, enviando o valor `true` para o pai.
