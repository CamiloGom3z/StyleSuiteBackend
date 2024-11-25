import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cita.reducer';

export const CitaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const citaEntity = useAppSelector(state => state.cita.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="citaDetailsHeading">
          <Translate contentKey="jhispterBackendApp.cita.detail.title">Cita</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{citaEntity.id}</dd>
          <dt>
            <span id="fechaCita">
              <Translate contentKey="jhispterBackendApp.cita.fechaCita">Fecha Cita</Translate>
            </span>
          </dt>
          <dd>{citaEntity.fechaCita ? <TextFormat value={citaEntity.fechaCita} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="jhispterBackendApp.cita.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{citaEntity.estado}</dd>
          <dt>
            <span id="personaId">
              <Translate contentKey="jhispterBackendApp.cita.personaId">Persona Id</Translate>
            </span>
          </dt>
          <dd>{citaEntity.personaId}</dd>
          <dt>
            <span id="nombrePersona">
              <Translate contentKey="jhispterBackendApp.cita.nombrePersona">Nombre Persona</Translate>
            </span>
          </dt>
          <dd>{citaEntity.nombrePersona}</dd>
          <dt>
            <span id="establecimientoId">
              <Translate contentKey="jhispterBackendApp.cita.establecimientoId">Establecimiento Id</Translate>
            </span>
          </dt>
          <dd>{citaEntity.establecimientoId}</dd>
          <dt>
            <span id="nombreEstablecimiento">
              <Translate contentKey="jhispterBackendApp.cita.nombreEstablecimiento">Nombre Establecimiento</Translate>
            </span>
          </dt>
          <dd>{citaEntity.nombreEstablecimiento}</dd>
          <dt>
            <span id="servicioId">
              <Translate contentKey="jhispterBackendApp.cita.servicioId">Servicio Id</Translate>
            </span>
          </dt>
          <dd>{citaEntity.servicioId}</dd>
          <dt>
            <span id="empleadoId">
              <Translate contentKey="jhispterBackendApp.cita.empleadoId">Empleado Id</Translate>
            </span>
          </dt>
          <dd>{citaEntity.empleadoId}</dd>
          <dt>
            <span id="nombreEmpleado">
              <Translate contentKey="jhispterBackendApp.cita.nombreEmpleado">Nombre Empleado</Translate>
            </span>
          </dt>
          <dd>{citaEntity.nombreEmpleado}</dd>
          <dt>
            <span id="valorServicio">
              <Translate contentKey="jhispterBackendApp.cita.valorServicio">Valor Servicio</Translate>
            </span>
          </dt>
          <dd>{citaEntity.valorServicio}</dd>
        </dl>
        <Button tag={Link} to="/cita" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cita/${citaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CitaDetail;
