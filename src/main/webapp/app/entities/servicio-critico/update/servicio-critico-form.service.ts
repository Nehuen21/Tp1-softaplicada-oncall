import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IServicioCritico, NewServicioCritico } from '../servicio-critico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicioCritico for edit and NewServicioCriticoFormGroupInput for create.
 */
type ServicioCriticoFormGroupInput = IServicioCritico | PartialWithRequiredKeyOf<NewServicioCritico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IServicioCritico | NewServicioCritico> = Omit<T, 'volvioCriticoEn'> & {
  volvioCriticoEn?: string | null;
};

type ServicioCriticoFormRawValue = FormValueOf<IServicioCritico>;

type NewServicioCriticoFormRawValue = FormValueOf<NewServicioCritico>;

type ServicioCriticoFormDefaults = Pick<NewServicioCritico, 'id' | 'volvioCriticoEn'>;

type ServicioCriticoFormGroupContent = {
  id: FormControl<ServicioCriticoFormRawValue['id'] | NewServicioCritico['id']>;
  volvioCriticoEn: FormControl<ServicioCriticoFormRawValue['volvioCriticoEn']>;
  motivo: FormControl<ServicioCriticoFormRawValue['motivo']>;
  servicio: FormControl<ServicioCriticoFormRawValue['servicio']>;
};

export type ServicioCriticoFormGroup = FormGroup<ServicioCriticoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicioCriticoFormService {
  createServicioCriticoFormGroup(servicioCritico?: ServicioCriticoFormGroupInput): ServicioCriticoFormGroup {
    const servicioCriticoRawValue = this.convertServicioCriticoToServicioCriticoRawValue({
      ...this.getFormDefaults(),
      ...(servicioCritico ?? { id: null }),
    });

    return new FormGroup<ServicioCriticoFormGroupContent>({
      id: new FormControl(
        { value: servicioCriticoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      volvioCriticoEn: new FormControl(servicioCriticoRawValue.volvioCriticoEn, {
        validators: [Validators.required],
      }),
      motivo: new FormControl(servicioCriticoRawValue.motivo, {
        validators: [Validators.maxLength(400)],
      }),
      servicio: new FormControl(servicioCriticoRawValue.servicio, {
        validators: [Validators.required],
      }),
    });
  }

  getServicioCritico(form: ServicioCriticoFormGroup): IServicioCritico | NewServicioCritico {
    return this.convertServicioCriticoRawValueToServicioCritico(form.getRawValue());
  }

  resetForm(form: ServicioCriticoFormGroup, servicioCritico: ServicioCriticoFormGroupInput): void {
    const servicioCriticoRawValue = this.convertServicioCriticoToServicioCriticoRawValue({ ...this.getFormDefaults(), ...servicioCritico });
    form.reset({
      ...servicioCriticoRawValue,
      id: { value: servicioCriticoRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): ServicioCriticoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      volvioCriticoEn: currentTime,
    };
  }

  private convertServicioCriticoRawValueToServicioCritico(
    rawServicioCritico: ServicioCriticoFormRawValue | NewServicioCriticoFormRawValue,
  ): IServicioCritico | NewServicioCritico {
    return {
      ...rawServicioCritico,
      volvioCriticoEn: dayjs(rawServicioCritico.volvioCriticoEn, DATE_TIME_FORMAT),
    };
  }

  private convertServicioCriticoToServicioCriticoRawValue(
    servicioCritico: IServicioCritico | (Partial<NewServicioCritico> & ServicioCriticoFormDefaults),
  ): ServicioCriticoFormRawValue | PartialWithRequiredKeyOf<NewServicioCriticoFormRawValue> {
    return {
      ...servicioCritico,
      volvioCriticoEn: servicioCritico.volvioCriticoEn ? servicioCritico.volvioCriticoEn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
