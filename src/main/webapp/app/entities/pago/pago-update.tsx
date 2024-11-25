import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPago } from 'app/shared/model/pago.model';
import { MetodoPagoEnum } from 'app/shared/model/enumerations/metodo-pago-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './pago.reducer';

export const PagoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const pagoEntity = useAppSelector(state => state.pago.entity);
  const loading = useAppSelector(state => state.pago.loading);
  const updating = useAppSelector(state => state.pago.updating);
  const updateSuccess = useAppSelector(state => state.pago.updateSuccess);
  const metodoPagoEnumValues = Object.keys(MetodoPagoEnum);
  const handleClose = () => {
    props.history.push('/pago' + props.location.search);
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
    values.fechaPago = convertDateTimeToServer(values.fechaPago);

    const entity = {
      ...pagoEntity,
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
          fechaPago: displayDefaultDateTime(),
        }
      : {
          metodoPago: 'EFECTIVO',
          ...pagoEntity,
          fechaPago: convertDateTimeFromServer(pagoEntity.fechaPago),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhispterBackendApp.pago.home.createOrEditLabel" data-cy="PagoCreateUpdateHeading">
            <Translate contentKey="jhispterBackendApp.pago.home.createOrEditLabel">Create or edit a Pago</Translate>
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
                  id="pago-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('jhispterBackendApp.pago.monto')} id="pago-monto" name="monto" data-cy="monto" type="text" />
              <ValidatedField
                label={translate('jhispterBackendApp.pago.fechaPago')}
                id="pago-fechaPago"
                name="fechaPago"
                data-cy="fechaPago"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.pago.metodoPago')}
                id="pago-metodoPago"
                name="metodoPago"
                data-cy="metodoPago"
                type="select"
              >
                {metodoPagoEnumValues.map(metodoPagoEnum => (
                  <option value={metodoPagoEnum} key={metodoPagoEnum}>
                    {translate('jhispterBackendApp.MetodoPagoEnum.' + metodoPagoEnum)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhispterBackendApp.pago.estado')}
                id="pago-estado"
                name="estado"
                data-cy="estado"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.pago.citaId')}
                id="pago-citaId"
                name="citaId"
                data-cy="citaId"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.pago.carritoId')}
                id="pago-carritoId"
                name="carritoId"
                data-cy="carritoId"
                type="text"
              />
              <ValidatedField
                label={translate('jhispterBackendApp.pago.userId')}
                id="pago-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pago" replace color="info">
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

export default PagoUpdate;
