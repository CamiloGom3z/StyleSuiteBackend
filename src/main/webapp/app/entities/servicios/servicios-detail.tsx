import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servicios.reducer';

export const ServiciosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const serviciosEntity = useAppSelector(state => state.servicios.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviciosDetailsHeading">
          <Translate contentKey="jhispterBackendApp.servicios.detail.title">Servicios</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.id}</dd>
          <dt>
            <span id="valorServicio">
              <Translate contentKey="jhispterBackendApp.servicios.valorServicio">Valor Servicio</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.valorServicio}</dd>
          <dt>
            <span id="tipoServicio">
              <Translate contentKey="jhispterBackendApp.servicios.tipoServicio">Tipo Servicio</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.tipoServicio}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhispterBackendApp.servicios.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.descripcion}</dd>
          <dt>
            <span id="establecimientoId">
              <Translate contentKey="jhispterBackendApp.servicios.establecimientoId">Establecimiento Id</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.establecimientoId}</dd>
        </dl>
        <Button tag={Link} to="/servicios" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servicios/${serviciosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiciosDetail;
