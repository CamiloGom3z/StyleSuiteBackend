import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Establecimiento from './establecimiento';
import EstablecimientoDetail from './establecimiento-detail';
import EstablecimientoUpdate from './establecimiento-update';
import EstablecimientoDeleteDialog from './establecimiento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EstablecimientoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EstablecimientoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EstablecimientoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Establecimiento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EstablecimientoDeleteDialog} />
  </>
);

export default Routes;
