import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './galeria-imagen.reducer';

export const GaleriaImagenDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const galeriaImagenEntity = useAppSelector(state => state.galeriaImagen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="galeriaImagenDetailsHeading">
          <Translate contentKey="jhispterBackendApp.galeriaImagen.detail.title">GaleriaImagen</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{galeriaImagenEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhispterBackendApp.galeriaImagen.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{galeriaImagenEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhispterBackendApp.galeriaImagen.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{galeriaImagenEntity.descripcion}</dd>
          <dt>
            <span id="urlImagen">
              <Translate contentKey="jhispterBackendApp.galeriaImagen.urlImagen">Url Imagen</Translate>
            </span>
          </dt>
          <dd>{galeriaImagenEntity.urlImagen}</dd>
          <dt>
            <span id="establecimientoId">
              <Translate contentKey="jhispterBackendApp.galeriaImagen.establecimientoId">Establecimiento Id</Translate>
            </span>
          </dt>
          <dd>{galeriaImagenEntity.establecimientoId}</dd>
        </dl>
        <Button tag={Link} to="/galeria-imagen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/galeria-imagen/${galeriaImagenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GaleriaImagenDetail;
