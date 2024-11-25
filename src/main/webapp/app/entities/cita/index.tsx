import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cita from './cita';
import CitaDetail from './cita-detail';
import CitaUpdate from './cita-update';
import CitaDeleteDialog from './cita-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CitaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CitaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CitaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cita} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CitaDeleteDialog} />
  </>
);

export default Routes;
