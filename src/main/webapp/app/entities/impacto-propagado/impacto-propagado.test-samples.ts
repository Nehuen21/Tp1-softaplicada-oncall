import dayjs from 'dayjs/esm';

import { IImpactoPropagado, NewImpactoPropagado } from './impacto-propagado.model';

export const sampleWithRequiredData: IImpactoPropagado = {
  id: 10250,
  severidad: 'SEV4',
  desde: dayjs('2023-12-03T23:16'),
};

export const sampleWithPartialData: IImpactoPropagado = {
  id: 20549,
  severidad: 'SEV4',
  desde: dayjs('2023-12-04T22:26'),
};

export const sampleWithFullData: IImpactoPropagado = {
  id: 20030,
  severidad: 'SEV4',
  desde: dayjs('2023-12-04T07:15'),
  hasta: dayjs('2023-12-04T10:33'),
};

export const sampleWithNewData: NewImpactoPropagado = {
  severidad: 'SEV3',
  desde: dayjs('2023-12-04T09:11'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
