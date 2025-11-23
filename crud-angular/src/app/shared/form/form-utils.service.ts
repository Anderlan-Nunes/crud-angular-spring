import { UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  validateAllFormFields(formGroup: UntypedFormGroup | UntypedFormArray) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof UntypedFormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (
        control instanceof UntypedFormGroup ||
        control instanceof UntypedFormArray
      ) {
        control.markAsTouched({ onlySelf: true });
        this.validateAllFormFields(control);
      }
    });
  }

   getErrorMessage(formGroup: UntypedFormGroup, fieldName: string) {
    const field = formGroup.get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field)
  }

  //pegar a mensagem diretamento do campo
    getErrorMessageFromField(field: UntypedFormControl) {
    if (field?.hasError('required')) {
      return 'Você deve inserir um valor';
    }

    if (field?.hasError('minlength')) {
      const requiredLength : number = field.errors ? field.errors['minlength']['requiredLength'] : 5;
      return `O tamanho mínimo é de ${requiredLength} caracteres`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength : number = field.errors ? field.errors['maxlength']['requiredLength'] : 100;
      return `O tamanho máximo é de ${requiredLength} caracteres`;
    }
    return 'Campo inválido';
  }

  getFormArrayFildErrorMessage(formGroup: UntypedFormGroup, formArrayName: string, fieldName: string, index: number) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    const field = formArray.controls[index].get(fieldName) as UntypedFormControl; //quando tem o index tenho acesso ao formgroup
    return this.getErrorMessageFromField(field)
  }

  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return !formArray.valid && formArray.hasError('required') && formArray.touched;
  }
}

/** onlySelf: true
 * O que ela diz sobre onlySelf: true:
Razão principal:
Quando você marca um FormGroup ou FormArray como touched sem essa opção, ele automaticamente marca todos os filhos também. Com onlySelf: true, você tem controle para fazer isso um por um, campo por campo, através da recursão.
Problema que resolve:
Se você não usar onlySelf: true, ao marcar campos como touched, isso vai disparar (trigger) observables que estejam escutando mudanças nos campos. Isso pode causar:

Múltiplos triggers desnecessários
Comportamento caótico na aplicação
Processamento excessivo de eventos de mudança

Benefício:
Dá mais controle sobre o que está sendo executado, evitando efeitos colaterais indesejados com observables e validações que escutam mudanças nos formulários.
É uma otimização importante para aplicações em produção com formulários complexos.

// Com onlySelf: true - apenas marca o campo específico
control.markAsTouched({ onlySelf: true });

// Sem a opção - marca o campo E todos os pais/filhos na hierarquia
control.markAsTouched();

*/
