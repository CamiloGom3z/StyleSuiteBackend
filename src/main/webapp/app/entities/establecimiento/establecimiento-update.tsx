import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstablecimiento } from 'app/shared/model/establecimiento.model';
import { getEntity, updateEntity, createEntity, reset } from './establecimiento.reducer';

export const EstablecimientoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const establecimientoEntity = useAppSelector(state => state.establecimiento.entity);
  const loading = useAppSelector(state => state.establecimiento.loading);
  const updating = useAppSelector(state => state.establecimiento.updating);
  const updateSuccess = useAppSelector(state => state.establecimiento.updateSuccess);
  const handleClose = () => {
    props.history.push('/establecimiento' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...establecimientoEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...establecimientoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhispterBackendApp.establecimiento.home.createOrEditLabel" data-cy="EstablecimientoCreateUpdateHeading">
            <Translate contentKey="jhispterBackendApp.establecimiento.home.createOrEditLabel">Create or edit a Establecimiento</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="establecimiento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhispterBackendApp.establecimiento.nombre')}
                id="establecimiento-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.establecimiento.direccion')}
                id="establecimiento-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.establecimiento.telefono')}
                id="establecimiento-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.establecimiento.correoElectronico')}
                id="establecimiento-correoElectronico"
                name="correoElectronico"
                data-cy="correoElectronico"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.establecimiento.urlImg')}
                id="establecimiento-urlImg"
                name="urlImg"
                data-cy="urlImg"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.establecimiento.userId')}
                id="establecimiento-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/establecimiento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EstablecimientoUpdate;
