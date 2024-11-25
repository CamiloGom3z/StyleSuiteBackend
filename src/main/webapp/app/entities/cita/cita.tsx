import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICita } from 'app/shared/model/cita.model';
import { getEntities } from './cita.reducer';

export const Cita = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const citaList = useAppSelector(state => state.cita.entities);
  const loading = useAppSelector(state => state.cita.loading);
  const totalItems = useAppSelector(state => state.cita.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="cita-heading" data-cy="CitaHeading">
        <Translate contentKey="jhispterBackendApp.cita.home.title">Citas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhispterBackendApp.cita.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/cita/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhispterBackendApp.cita.home.createLabel">Create new Cita</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {citaList && citaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhispterBackendApp.cita.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaCita')}>
                  <Translate contentKey="jhispterBackendApp.cita.fechaCita">Fecha Cita</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('estado')}>
                  <Translate contentKey="jhispterBackendApp.cita.estado">Estado</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('personaId')}>
                  <Translate contentKey="jhispterBackendApp.cita.personaId">Persona Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombrePersona')}>
                  <Translate contentKey="jhispterBackendApp.cita.nombrePersona">Nombre Persona</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('establecimientoId')}>
                  <Translate contentKey="jhispterBackendApp.cita.establecimientoId">Establecimiento Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreEstablecimiento')}>
                  <Translate contentKey="jhispterBackendApp.cita.nombreEstablecimiento">Nombre Establecimiento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servicioId')}>
                  <Translate contentKey="jhispterBackendApp.cita.servicioId">Servicio Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('empleadoId')}>
                  <Translate contentKey="jhispterBackendApp.cita.empleadoId">Empleado Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreEmpleado')}>
                  <Translate contentKey="jhispterBackendApp.cita.nombreEmpleado">Nombre Empleado</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorServicio')}>
                  <Translate contentKey="jhispterBackendApp.cita.valorServicio">Valor Servicio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {citaList.map((cita, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/cita/${cita.id}`} color="link" size="sm">
                      {cita.id}
                    </Button>
                  </td>
                  <td>{cita.fechaCita ? <TextFormat type="date" value={cita.fechaCita} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`jhispterBackendApp.EstadoCitaEnum.${cita.estado}`} />
                  </td>
                  <td>{cita.personaId}</td>
                  <td>{cita.nombrePersona}</td>
                  <td>{cita.establecimientoId}</td>
                  <td>{cita.nombreEstablecimiento}</td>
                  <td>{cita.servicioId}</td>
                  <td>{cita.empleadoId}</td>
                  <td>{cita.nombreEmpleado}</td>
                  <td>{cita.valorServicio}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/cita/${cita.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cita/${cita.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cita/${cita.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhispterBackendApp.cita.home.notFound">No Citas found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={citaList && citaList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Cita;
