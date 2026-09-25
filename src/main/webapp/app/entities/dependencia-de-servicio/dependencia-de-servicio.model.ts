import { PesoDependencia } from 'app/entities/enumerations/peso-dependencia.model';
import { IServicio } from 'app/entities/servicio/servicio.model';

export interface IDependenciaDeServicio {
  id: number;
  peso?: keyof typeof PesoDependencia | null;
  descripcion?: string | null;
  dependiente?: Pick<IServicio, 'id' | 'nombre'> | null;
  origen?: Pick<IServicio, 'id' | 'nombre'> | null;
}

export type NewDependenciaDeServicio = Omit<IDependenciaDeServicio, 'id'> & { id: null };
