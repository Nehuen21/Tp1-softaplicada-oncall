import dayjs from 'dayjs/esm';

import { IServicio } from 'app/entities/servicio/servicio.model';

export interface IServicioCritico {
  id: number;
  volvioCriticoEn?: dayjs.Dayjs | null;
  motivo?: string | null;
  servicio?: Pick<IServicio, 'id' | 'nombre'> | null;
}

export type NewServicioCritico = Omit<IServicioCritico, 'id'> & { id: null };
