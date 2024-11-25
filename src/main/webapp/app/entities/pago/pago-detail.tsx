import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pago.reducer';

export const PagoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pagoEntity = useAppSelector(state => state.pago.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pagoDetailsHeading">
          <Translate contentKey="jhispterBackendApp.pago.detail.title">Pago</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.id}</dd>
          <dt>
            <span id="monto">
              <Translate contentKey="jhispterBackendApp.pago.monto">Monto</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.monto}</dd>
          <dt>
            <span id="fechaPago">
              <Translate contentKey="jhispterBackendApp.pago.fechaPago">Fecha Pago</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.fechaPago ? <TextFormat value={pagoEntity.fechaPago} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="metodoPago">
              <Translate contentKey="jhispterBackendApp.pago.metodoPago">Metodo Pago</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.metodoPago}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="jhispterBackendApp.pago.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.estado}</dd>
          <dt>
            <span id="citaId">
              <Translate contentKey="jhispterBackendApp.pago.citaId">Cita Id</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.citaId}</dd>
          <dt>
            <span id="carritoId">
              <Translate contentKey="jhispterBackendApp.pago.carritoId">Carrito Id</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.carritoId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="jhispterBackendApp.pago.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.userId}</dd>
        </dl>
        <Button tag={Link} to="/pago" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pago/${pagoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PagoDetail;
