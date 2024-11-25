import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICita } from 'app/shared/model/cita.model';
import { EstadoCitaEnum } from 'app/shared/model/enumerations/estado-cita-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './cita.reducer';

export const CitaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const citaEntity = useAppSelector(state => state.cita.entity);
  const loading = useAppSelector(state => state.cita.loading);
  const updating = useAppSelector(state => state.cita.updating);
  const updateSuccess = useAppSelector(state => state.cita.updateSuccess);
  const estadoCitaEnumValues = Object.keys(EstadoCitaEnum);
  const handleClose = () => {
    props.history.push('/cita' + props.location.search);
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
    values.fechaCita = convertDateTimeToServer(values.fechaCita);

    const entity = {
      ...citaEntity,
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
      ? {
          fechaCita: displayDefaultDateTime(),
        }
      : {
          estado: 'PENDIENTE',
          ...citaEntity,
          fechaCita: convertDateTimeFromServer(citaEntity.fechaCita),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhispterBackendApp.cita.home.createOrEditLabel" data-cy="CitaCreateUpdateHeading">
            <Translate contentKey="jhispterBackendApp.cita.home.createOrEditLabel">Create or edit a Cita</Translate>
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
                  id="cita-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhispterBackendApp.cita.fechaCita')}
                id="cita-fechaCita"
                name="fechaCita"
                data-cy="fechaCita"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.estado')}
                id="cita-estado"
                name="estado"
                data-cy="estado"
                type="select"
              >
                {estadoCitaEnumValues.map(estadoCitaEnum => (
                  <option value={estadoCitaEnum} key={estadoCitaEnum}>
                    {translate('jhispterBackendApp.EstadoCitaEnum.' + estadoCitaEnum)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhispterBackendApp.cita.personaId')}
                id="cita-personaId"
                name="personaId"
                data-cy="personaId"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.nombrePersona')}
                id="cita-nombrePersona"
                name="nombrePersona"
                data-cy="nombrePersona"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.establecimientoId')}
                id="cita-establecimientoId"
                name="establecimientoId"
                data-cy="establecimientoId"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.nombreEstablecimiento')}
                id="cita-nombreEstablecimiento"
                name="nombreEstablecimiento"
                data-cy="nombreEstablecimiento"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.servicioId')}
                id="cita-servicioId"
                name="servicioId"
                data-cy="servicioId"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.empleadoId')}
                id="cita-empleadoId"
                name="empleadoId"
                data-cy="empleadoId"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.nombreEmpleado')}
                id="cita-nombreEmpleado"
                name="nombreEmpleado"
                data-cy="nombreEmpleado"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.cita.valorServicio')}
                id="cita-valorServicio"
                name="valorServicio"
                data-cy="valorServicio"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cita" replace color="info">
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

export default CitaUpdate;
