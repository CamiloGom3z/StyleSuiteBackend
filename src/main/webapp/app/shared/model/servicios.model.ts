export interface IServicios {
  id?: number;
  valorServicio?: number | null;
  tipoServicio?: string | null;
  descripcion?: string | null;
  establecimientoId?: number | null;
}

export const defaultValue: Readonly<IServicios> = {};
