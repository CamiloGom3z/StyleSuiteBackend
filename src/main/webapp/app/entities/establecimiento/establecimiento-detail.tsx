import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './establecimiento.reducer';

export const EstablecimientoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const establecimientoEntity = useAppSelector(state => state.establecimiento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="establecimientoDetailsHeading">
          <Translate contentKey="jhispterBackendApp.establecimiento.detail.title">Establecimiento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhispterBackendApp.establecimiento.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.nombre}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="jhispterBackendApp.establecimiento.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.direccion}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="jhispterBackendApp.establecimiento.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.telefono}</dd>
          <dt>
            <span id="correoElectronico">
              <Translate contentKey="jhispterBackendApp.establecimiento.correoElectronico">Correo Electronico</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.correoElectronico}</dd>
          <dt>
            <span id="urlImg">
              <Translate contentKey="jhispterBackendApp.establecimiento.urlImg">Url Img</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.urlImg}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="jhispterBackendApp.establecimiento.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{establecimientoEntity.userId}</dd>
        </dl>
        <Button tag={Link} to="/establecimiento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/establecimiento/${establecimientoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EstablecimientoDetail;
