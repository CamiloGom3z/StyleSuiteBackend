import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './empleado.reducer';

export const EmpleadoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const empleadoEntity = useAppSelector(state => state.empleado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="empleadoDetailsHeading">
          <Translate contentKey="jhispterBackendApp.empleado.detail.title">Empleado</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhispterBackendApp.empleado.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="jhispterBackendApp.empleado.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.apellido}</dd>
          <dt>
            <span id="cargo">
              <Translate contentKey="jhispterBackendApp.empleado.cargo">Cargo</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.cargo}</dd>
          <dt>
            <span id="salario">
              <Translate contentKey="jhispterBackendApp.empleado.salario">Salario</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.salario}</dd>
          <dt>
            <span id="urlmg">
              <Translate contentKey="jhispterBackendApp.empleado.urlmg">Urlmg</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.urlmg}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="jhispterBackendApp.empleado.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.estado}</dd>
          <dt>
            <span id="establecimientoId">
              <Translate contentKey="jhispterBackendApp.empleado.establecimientoId">Establecimiento Id</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.establecimientoId}</dd>
        </dl>
        <Button tag={Link} to="/empleado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/empleado/${empleadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmpleadoDetail;
