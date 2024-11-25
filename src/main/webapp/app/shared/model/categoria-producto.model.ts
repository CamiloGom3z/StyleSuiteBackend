export interface ICategoriaProducto {
  id?: number;
  nombre?: string | null;
  establecimientoId?: number | null;
}

export const defaultValue: Readonly<ICategoriaProducto> = {};
