import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './persona.reducer';

export const PersonaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personaEntity = useAppSelector(state => state.persona.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personaDetailsHeading">
          <Translate contentKey="jhispterBackendApp.persona.detail.title">Persona</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhispterBackendApp.persona.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{personaEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="jhispterBackendApp.persona.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{personaEntity.apellido}</dd>
          <dt>
            <span id="urlImg">
              <Translate contentKey="jhispterBackendApp.persona.urlImg">Url Img</Translate>
            </span>
          </dt>
          <dd>{personaEntity.urlImg}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="jhispterBackendApp.persona.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{personaEntity.userId}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="jhispterBackendApp.persona.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{personaEntity.telefono}</dd>
          <dt>
            <span id="urlmg">
              <Translate contentKey="jhispterBackendApp.persona.urlmg">Urlmg</Translate>
            </span>
          </dt>
          <dd>{personaEntity.urlmg}</dd>
        </dl>
        <Button tag={Link} to="/persona" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/persona/${personaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonaDetail;
