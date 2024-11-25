export interface IPersona {
  id?: number;
  nombre?: string | null;
  apellido?: string | null;
  urlImg?: string | null;
  userId?: number | null;
  telefono?: string | null;
  urlmg?: string | null;
}

export const defaultValue: Readonly<IPersona> = {};
