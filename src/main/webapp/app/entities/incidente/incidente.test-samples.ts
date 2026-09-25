import dayjs from 'dayjs/esm';

import { IIncidente, NewIncidente } from './incidente.model';

export const sampleWithRequiredData: IIncidente = {
  id: 10165,
  titulo: 'to but aha',
  severidad: 'SEV3',
  estado: 'ABIERTO',
  detectadoEn: dayjs('2023-12-04T06:36'),
};

export const sampleWithPartialData: IIncidente = {
  id: 6822,
  titulo: 'eek',
  severidad: 'SEV2',
  estado: 'CERRADO',
  detectadoEn: dayjs('2023-12-03T23:17'),
  reconocidoEn: dayjs('2023-12-03T23:19'),
  mitigadoEn: dayjs('2023-12-04T14:10'),
  usuariosAfectados: 5399,
};

export const sampleWithFullData: IIncidente = {
  id: 14089,
  titulo: 'embossing gosh pace',
  descripcion: 'eyeliner',
  severidad: 'SEV4',
  estado: 'ABIERTO',
  detectadoEn: dayjs('2023-12-04T22:18'),
  reconocidoEn: dayjs('2023-12-04T12:12'),
  mitigadoEn: dayjs('2023-12-04T15:20'),
  resueltoEn: dayjs('2023-12-04T06:41'),
  usuariosAfectados: 32687,
  cumplioObjetivo: false,
};

export const sampleWithNewData: NewIncidente = {
  titulo: 'crest deplore',
  severidad: 'SEV3',
  estado: 'ABIERTO',
  detectadoEn: dayjs('2023-12-04T18:14'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
