import { IDependenciaDeServicio, NewDependenciaDeServicio } from './dependencia-de-servicio.model';

export const sampleWithRequiredData: IDependenciaDeServicio = {
  id: 11744,
  peso: 'DEGRADA',
};

export const sampleWithPartialData: IDependenciaDeServicio = {
  id: 14345,
  peso: 'DEGRADA',
  descripcion: 'ostrich considering',
};

export const sampleWithFullData: IDependenciaDeServicio = {
  id: 26957,
  peso: 'AFECTA',
  descripcion: 'inasmuch',
};

export const sampleWithNewData: NewDependenciaDeServicio = {
  peso: 'AFECTA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
