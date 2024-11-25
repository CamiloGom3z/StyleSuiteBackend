import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Persona from './persona';
import Empleado from './empleado';
import Cita from './cita';
import Servicios from './servicios';
import Establecimiento from './establecimiento';
import Producto from './producto';
import CategoriaProducto from './categoria-producto';
import GaleriaImagen from './galeria-imagen';
import Pago from './pago';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}persona`} component={Persona} />
        <ErrorBoundaryRoute path={`${match.url}empleado`} component={Empleado} />
        <ErrorBoundaryRoute path={`${match.url}cita`} component={Cita} />
        <ErrorBoundaryRoute path={`${match.url}servicios`} component={Servicios} />
        <ErrorBoundaryRoute path={`${match.url}establecimiento`} component={Establecimiento} />
        <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
        <ErrorBoundaryRoute path={`${match.url}categoria-producto`} component={CategoriaProducto} />
        <ErrorBoundaryRoute path={`${match.url}galeria-imagen`} component={GaleriaImagen} />
        <ErrorBoundaryRoute path={`${match.url}pago`} component={Pago} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
