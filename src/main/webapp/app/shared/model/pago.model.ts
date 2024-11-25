import dayjs from 'dayjs';
import { MetodoPagoEnum } from 'app/shared/model/enumerations/metodo-pago-enum.model';

export interface IPago {
  id?: number;
  monto?: number | null;
  fechaPago?: string | null;
  metodoPago?: MetodoPagoEnum | null;
  estado?: string | null;
  citaId?: number | null;
  carritoId?: number | null;
  userId?: number | null;
}

export const defaultValue: Readonly<IPago> = {};
