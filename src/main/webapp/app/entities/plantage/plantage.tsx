import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './plantage.reducer';

export const Plantage = () => {
  const dispatch = useAppDispatch();
  const [planteLibelles, setplanteLibelles] = useState({});
  const [parcelleLibelles, setparcelleLibelles] = useState({});

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const plantageList = useAppSelector(state => state.plantage.entities);
  const loading = useAppSelector(state => state.plantage.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
        search: searchTerm, // Ajoutez le terme de recherche
      }),
    );
  };

  useEffect(() => {
    const fetchPlantTypeNames = async () => {
      await Promise.all(
        plantageList.map(async plantage => {
          const planteLibellesResponse = await fetch(`/api/plantages/${plantage.id}/plante-libelle`);
          const parcelleLibellesResponse = await fetch(`/api/plantages/${plantage.id}/parcelle-libelle`);
          const parcelleLibelle = await parcelleLibellesResponse.text();
          const planteLibelle = await planteLibellesResponse.text();
          setplanteLibelles(prevState => ({ ...prevState, [plantage.id]: planteLibelle }));
          setparcelleLibelles(prevState => ({ ...prevState, [plantage.id]: parcelleLibelle }));
        }),
      );
    };

    fetchPlantTypeNames();
  }, [plantageList]);

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const handleSearchChange = e => {
    setSearchTerm(e.target.value);
    console.log('Search Term:', e.target.value);
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };
  const filteredplantageList = plantageList.filter(plantage => {
    const searchLower = searchTerm.toLowerCase();
    return (
      (typeof plantage.nombre === 'string' && plantage.nombre.toLowerCase().includes(searchLower)) ||
      (typeof planteLibelles[plantage.id] === 'string' && planteLibelles[plantage.id].toLowerCase().includes(searchLower)) ||
      (typeof plantage.parcelleLibelle === 'string' && plantage.parcelleLibelle.toLowerCase().includes(searchLower))
    );
  });

  return (
    <div>
      <h2 id="plantage-heading" data-cy="PlantageHeading">
        <Translate contentKey="gestionDesFermesApp.plantage.home.title">Plantages</Translate>

        <div className="d-flex justify-content-end mb-2">
          <div className="me-2">
            <input
              type="text"
              placeholder="Rechercher..."
              value={searchTerm}
              onChange={e => setSearchTerm(e.target.value)}
              className="form-control form-control-sm"
            />
          </div>
        </div>

        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gestionDesFermesApp.plantage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/plantage/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gestionDesFermesApp.plantage.home.createLabel">Create new Plantage</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {filteredplantageList && filteredplantageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="gestionDesFermesApp.plantage.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="gestionDesFermesApp.plantage.date">Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th className="hand" onClick={sort('nombre')}>
                  <Translate contentKey="gestionDesFermesApp.plantage.nombre">Nombre</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nombre')} />
                </th>
                <th>
                  <Translate contentKey="gestionDesFermesApp.plantage.planteLibelle">Plante </Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="gestionDesFermesApp.plantage.parcelleLibelle">Parcelle </Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredplantageList.map((plantage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/plantage/${plantage.id}`} color="link" size="sm">
                      {plantage.id}
                    </Button>
                  </td>
                  <td>{plantage.date ? <TextFormat type="date" value={plantage.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{plantage.nombre}</td>
                  <td>
                    <td>
                      {planteLibelles[plantage.id] ? (
                        <Link to={`/plante/${plantage.planteLibelle.id}`}>{planteLibelles[plantage.id]}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                  </td>

                  <td>
                    {parcelleLibelles[plantage.id] ? (
                      <Link to={`/parcelle/${plantage.parcelleLibelle.id}`}>{parcelleLibelles[plantage.id]}</Link>
                    ) : (
                      ''
                    )}
                  </td>

                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/plantage/${plantage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/plantage/${plantage.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/plantage/${plantage.id}/delete`)}
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
              <Translate contentKey="gestionDesFermesApp.plantage.home.notFound">No Plantages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Plantage;
