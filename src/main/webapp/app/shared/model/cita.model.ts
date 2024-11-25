import dayjs from 'dayjs';
import { EstadoCitaEnum } from 'app/shared/model/enumerations/estado-cita-enum.model';

export interface ICita {
  id?: number;
  fechaCita?: string | null;
  estado?: EstadoCitaEnum | null;
  personaId?: number | null;
  nombrePersona?: string | null;
  establecimientoId?: number | null;
  nombreEstablecimiento?: string | null;
  servicioId?: number | null;
  empleadoId?: number | null;
  nombreEmpleado?: string | null;
  valorServicio?: number | null;
}

export const defaultValue: Readonly<ICita> = {};
