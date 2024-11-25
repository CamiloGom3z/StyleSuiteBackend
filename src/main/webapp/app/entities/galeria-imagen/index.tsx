import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GaleriaImagen from './galeria-imagen';
import GaleriaImagenDetail from './galeria-imagen-detail';
import GaleriaImagenUpdate from './galeria-imagen-update';
import GaleriaImagenDeleteDialog from './galeria-imagen-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GaleriaImagenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GaleriaImagenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GaleriaImagenDetail} />
      <ErrorBoundaryRoute path={match.url} component={GaleriaImagen} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GaleriaImagenDeleteDialog} />
  </>
);

export default Routes;
