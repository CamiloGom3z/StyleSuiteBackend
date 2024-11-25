import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CategoriaProducto from './categoria-producto';
import CategoriaProductoDetail from './categoria-producto-detail';
import CategoriaProductoUpdate from './categoria-producto-update';
import CategoriaProductoDeleteDialog from './categoria-producto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CategoriaProductoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CategoriaProductoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CategoriaProductoDetail} />
      <ErrorBoundaryRoute path={match.url} component={CategoriaProducto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CategoriaProductoDeleteDialog} />
  </>
);

export default Routes;
