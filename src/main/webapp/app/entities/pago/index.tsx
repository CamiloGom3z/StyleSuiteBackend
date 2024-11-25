import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pago from './pago';
import PagoDetail from './pago-detail';
import PagoUpdate from './pago-update';
import PagoDeleteDialog from './pago-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PagoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PagoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PagoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pago} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PagoDeleteDialog} />
  </>
);

export default Routes;
