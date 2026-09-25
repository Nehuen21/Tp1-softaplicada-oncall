import dayjs from 'dayjs/esm';

import { ITurnoDeGuardia, NewTurnoDeGuardia } from './turno-de-guardia.model';

export const sampleWithRequiredData: ITurnoDeGuardia = {
  id: 14445,
  desde: dayjs('2023-12-04T10:53'),
  hasta: dayjs('2023-12-04T08:15'),
};

export const sampleWithPartialData: ITurnoDeGuardia = {
  id: 17893,
  desde: dayjs('2023-12-04T03:17'),
  hasta: dayjs('2023-12-03T23:01'),
  esReemplazo: false,
  nota: 'on',
};

export const sampleWithFullData: ITurnoDeGuardia = {
  id: 17895,
  desde: dayjs('2023-12-04T14:24'),
  hasta: dayjs('2023-12-04T14:26'),
  esReemplazo: true,
  nota: 'suddenly now',
};

export const sampleWithNewData: NewTurnoDeGuardia = {
  desde: dayjs('2023-12-04T19:56'),
  hasta: dayjs('2023-12-04T05:41'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
