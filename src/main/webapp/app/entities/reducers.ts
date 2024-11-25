import persona from 'app/entities/persona/persona.reducer';
import empleado from 'app/entities/empleado/empleado.reducer';
import cita from 'app/entities/cita/cita.reducer';
import servicios from 'app/entities/servicios/servicios.reducer';
import establecimiento from 'app/entities/establecimiento/establecimiento.reducer';
import producto from 'app/entities/producto/producto.reducer';
import categoriaProducto from 'app/entities/categoria-producto/categoria-producto.reducer';
import galeriaImagen from 'app/entities/galeria-imagen/galeria-imagen.reducer';
import pago from 'app/entities/pago/pago.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  persona,
  empleado,
  cita,
  servicios,
  establecimiento,
  producto,
  categoriaProducto,
  galeriaImagen,
  pago,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
