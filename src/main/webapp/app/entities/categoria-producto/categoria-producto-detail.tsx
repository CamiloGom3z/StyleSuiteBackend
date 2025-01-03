import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './categoria-producto.reducer';

export const CategoriaProductoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const categoriaProductoEntity = useAppSelector(state => state.categoriaProducto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoriaProductoDetailsHeading">
          <Translate contentKey="jhispterBackendApp.categoriaProducto.detail.title">CategoriaProducto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoriaProductoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhispterBackendApp.categoriaProducto.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{categoriaProductoEntity.nombre}</dd>
          <dt>
            <span id="establecimientoId">
              <Translate contentKey="jhispterBackendApp.categoriaProducto.establecimientoId">Establecimiento Id</Translate>
            </span>
          </dt>
          <dd>{categoriaProductoEntity.establecimientoId}</dd>
        </dl>
        <Button tag={Link} to="/categoria-producto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categoria-producto/${categoriaProductoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoriaProductoDetail;
