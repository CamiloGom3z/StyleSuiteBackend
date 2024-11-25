import { EstadoEmpleadoEnum } from 'app/shared/model/enumerations/estado-empleado-enum.model';

export interface IEmpleado {
  id?: number;
  nombre?: string | null;
  apellido?: string | null;
  cargo?: string | null;
  salario?: number | null;
  urlmg?: string | null;
  estado?: EstadoEmpleadoEnum | null;
  establecimientoId?: number | null;
}

export const defaultValue: Readonly<IEmpleado> = {};
