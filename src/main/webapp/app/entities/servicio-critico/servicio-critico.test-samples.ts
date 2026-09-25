import dayjs from 'dayjs/esm';

import { IServicioCritico, NewServicioCritico } from './servicio-critico.model';

export const sampleWithRequiredData: IServicioCritico = {
  id: 25157,
  volvioCriticoEn: dayjs('2023-12-04T07:26'),
};

export const sampleWithPartialData: IServicioCritico = {
  id: 31301,
  volvioCriticoEn: dayjs('2023-12-04T01:16'),
  motivo: 'near until',
};

export const sampleWithFullData: IServicioCritico = {
  id: 30762,
  volvioCriticoEn: dayjs('2023-12-04T19:36'),
  motivo: 'meh beyond',
};

export const sampleWithNewData: NewServicioCritico = {
  volvioCriticoEn: dayjs('2023-12-04T21:49'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
