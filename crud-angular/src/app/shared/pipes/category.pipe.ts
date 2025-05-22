import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  transform(value: string): string {
    switch(value) {
      case 'Front-end':
        return 'code';
      case 'Back-end':
        return 'computer';
      case 'Full-stack':
        return 'developer_mode_tv'
    }
    return 'code'; // Retorna o valor original se não houver correspondência
  }

}

/* pensei em vez de usar switch case, usar uma constante com chave valor:

const categoryIconMap: {[key:string]:string} = {
nomeDaCategoria1: NomeDoIcon1,
nomeDaCategoria2: NomeDoIcon2,
nomeDaCategoria3: NomeDoIcon3,
nomeDaCategoria4: NomeDoIcon4,
}

E no final você só retorna o ícone da chave que vc enviar no Pipe:

return categoryIconMap[valorRecebidoPeloPipe] || "Nome DeIconeGenérico";

Ficaria menos verboso

O conceito da utilização do pipe para alterar os icons foi interessante.

Nas minhas aplicações (noutras linguagens) utilizo sempre uma tabela de Base de Dados onde defino, por exemplo os icons.
Desse modo, o número e "icons" pode ser alterado, os icons podem mudar em tempo de execução e não defino isso em Hardcode.

Penso que será sempre a melhor solução........
Não sei se em desenvolvimento para a Web possam existir melhores soluções????
*/


