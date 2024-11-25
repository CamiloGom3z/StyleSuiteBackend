export interface IEstablecimiento {
  id?: number;
  nombre?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  correoElectronico?: string | null;
  urlImg?: string | null;
  userId?: number | null;
}

export const defaultValue: Readonly<IEstablecimiento> = {};
