import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IImpactoPropagado, NewImpactoPropagado } from '../impacto-propagado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImpactoPropagado for edit and NewImpactoPropagadoFormGroupInput for create.
 */
type ImpactoPropagadoFormGroupInput = IImpactoPropagado | PartialWithRequiredKeyOf<NewImpactoPropagado>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IImpactoPropagado | NewImpactoPropagado> = Omit<T, 'desde' | 'hasta'> & {
  desde?: string | null;
  hasta?: string | null;
};

type ImpactoPropagadoFormRawValue = FormValueOf<IImpactoPropagado>;

type NewImpactoPropagadoFormRawValue = FormValueOf<NewImpactoPropagado>;

type ImpactoPropagadoFormDefaults = Pick<NewImpactoPropagado, 'id' | 'desde' | 'hasta'>;

type ImpactoPropagadoFormGroupContent = {
  id: FormControl<ImpactoPropagadoFormRawValue['id'] | NewImpactoPropagado['id']>;
  severidad: FormControl<ImpactoPropagadoFormRawValue['severidad']>;
  desde: FormControl<ImpactoPropagadoFormRawValue['desde']>;
  hasta: FormControl<ImpactoPropagadoFormRawValue['hasta']>;
  incidente: FormControl<ImpactoPropagadoFormRawValue['incidente']>;
  servicio: FormControl<ImpactoPropagadoFormRawValue['servicio']>;
  dependencia: FormControl<ImpactoPropagadoFormRawValue['dependencia']>;
};

export type ImpactoPropagadoFormGroup = FormGroup<ImpactoPropagadoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpactoPropagadoFormService {
  createImpactoPropagadoFormGroup(impactoPropagado?: ImpactoPropagadoFormGroupInput): ImpactoPropagadoFormGroup {
    const impactoPropagadoRawValue = this.convertImpactoPropagadoToImpactoPropagadoRawValue({
      ...this.getFormDefaults(),
      ...(impactoPropagado ?? { id: null }),
    });

    return new FormGroup<ImpactoPropagadoFormGroupContent>({
      id: new FormControl(
        { value: impactoPropagadoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      severidad: new FormControl(impactoPropagadoRawValue.severidad, {
        validators: [Validators.required],
      }),
      desde: new FormControl(impactoPropagadoRawValue.desde, {
        validators: [Validators.required],
      }),
      hasta: new FormControl(impactoPropagadoRawValue.hasta),
      incidente: new FormControl(impactoPropagadoRawValue.incidente, {
        validators: [Validators.required],
      }),
      servicio: new FormControl(impactoPropagadoRawValue.servicio, {
        validators: [Validators.required],
      }),
      dependencia: new FormControl(impactoPropagadoRawValue.dependencia, {
        validators: [Validators.required],
      }),
    });
  }

  getImpactoPropagado(form: ImpactoPropagadoFormGroup): IImpactoPropagado | NewImpactoPropagado {
    return this.convertImpactoPropagadoRawValueToImpactoPropagado(form.getRawValue());
  }

  resetForm(form: ImpactoPropagadoFormGroup, impactoPropagado: ImpactoPropagadoFormGroupInput): void {
    const impactoPropagadoRawValue = this.convertImpactoPropagadoToImpactoPropagadoRawValue({
      ...this.getFormDefaults(),
      ...impactoPropagado,
    });
    form.reset({
      ...impactoPropagadoRawValue,
      id: { value: impactoPropagadoRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): ImpactoPropagadoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      desde: currentTime,
      hasta: currentTime,
    };
  }

  private convertImpactoPropagadoRawValueToImpactoPropagado(
    rawImpactoPropagado: ImpactoPropagadoFormRawValue | NewImpactoPropagadoFormRawValue,
  ): IImpactoPropagado | NewImpactoPropagado {
    return {
      ...rawImpactoPropagado,
      desde: dayjs(rawImpactoPropagado.desde, DATE_TIME_FORMAT),
      hasta: dayjs(rawImpactoPropagado.hasta, DATE_TIME_FORMAT),
    };
  }

  private convertImpactoPropagadoToImpactoPropagadoRawValue(
    impactoPropagado: IImpactoPropagado | (Partial<NewImpactoPropagado> & ImpactoPropagadoFormDefaults),
  ): ImpactoPropagadoFormRawValue | PartialWithRequiredKeyOf<NewImpactoPropagadoFormRawValue> {
    return {
      ...impactoPropagado,
      desde: impactoPropagado.desde ? impactoPropagado.desde.format(DATE_TIME_FORMAT) : undefined,
      hasta: impactoPropagado.hasta ? impactoPropagado.hasta.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
