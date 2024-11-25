import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGaleriaImagen } from 'app/shared/model/galeria-imagen.model';
import { getEntity, updateEntity, createEntity, reset } from './galeria-imagen.reducer';

export const GaleriaImagenUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const galeriaImagenEntity = useAppSelector(state => state.galeriaImagen.entity);
  const loading = useAppSelector(state => state.galeriaImagen.loading);
  const updating = useAppSelector(state => state.galeriaImagen.updating);
  const updateSuccess = useAppSelector(state => state.galeriaImagen.updateSuccess);
  const handleClose = () => {
    props.history.push('/galeria-imagen' + props.location.search);
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
      ...galeriaImagenEntity,
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
          ...galeriaImagenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhispterBackendApp.galeriaImagen.home.createOrEditLabel" data-cy="GaleriaImagenCreateUpdateHeading">
            <Translate contentKey="jhispterBackendApp.galeriaImagen.home.createOrEditLabel">Create or edit a GaleriaImagen</Translate>
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
                  id="galeria-imagen-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhispterBackendApp.galeriaImagen.nombre')}
                id="galeria-imagen-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.galeriaImagen.descripcion')}
                id="galeria-imagen-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.galeriaImagen.urlImagen')}
                id="galeria-imagen-urlImagen"
                name="urlImagen"
                data-cy="urlImagen"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.galeriaImagen.establecimientoId')}
                id="galeria-imagen-establecimientoId"
                name="establecimientoId"
                data-cy="establecimientoId"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/galeria-imagen" replace color="info">
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

export default GaleriaImagenUpdate;
