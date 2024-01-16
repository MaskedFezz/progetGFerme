import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './plante.reducer';

export const Plante = () => {
  const dispatch = useAppDispatch();
  const [planteTypeNoms, setPlanteTypeNoms] = useState({});
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const planteList = useAppSelector(state => state.plante.entities);
  const loading = useAppSelector(state => state.plante.loading);

  // const getAllEntities = () => {
  //   dispatch(
  //     getEntities({
  //       sort: `${sortState.sort},${sortState.order}`,
  //     }),
  //   );
  // };

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
        planteList.map(async plante => {
          const planteTypeNomResponse = await fetch(`/api/plantes/${plante.id}/type-plante-nom`);

          const planteTypeNom = await planteTypeNomResponse.text();
          setPlanteTypeNoms(prevState => ({ ...prevState, [plante.id]: planteTypeNom }));
        }),
      );
    };

    fetchPlantTypeNames();
  }, [planteList]);

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
  const filteredPlanteList = planteList.filter(plante => {
    const searchLower = searchTerm.toLowerCase();

    return (
      plante.planteLibelle.toLowerCase().includes(searchLower) ||
      plante.racine.toLowerCase().includes(searchLower) ||
      planteTypeNoms[plante.id].toLowerCase().includes(searchLower)
    );
  });
  return (
    <div>
      <h2 id="plante-heading" data-cy="PlanteHeading">
        <Translate contentKey="gestionDesFermesApp.plante.home.title">Plantes</Translate>
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
            <Translate contentKey="gestionDesFermesApp.plante.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/plante/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gestionDesFermesApp.plante.home.createLabel">Create new Plante</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {filteredPlanteList && filteredPlanteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="gestionDesFermesApp.plante.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('planteLibelle')}>
                  <Translate contentKey="gestionDesFermesApp.plante.planteLibelle">Plante Libelle</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('planteLibelle')} />
                </th>
                <th className="hand" onClick={sort('racine')}>
                  <Translate contentKey="gestionDesFermesApp.plante.racine">Racine</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('racine')} />
                </th>
                <th className="hand" onClick={sort('photo')}>
                  <Translate contentKey="gestionDesFermesApp.plante.photo">Photo</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('photo')} />
                </th>
                <th>
                  <Translate contentKey="gestionDesFermesApp.plante.nom">Type de plante</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredPlanteList.map((plante, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/plante/${plante.id}`} color="link" size="sm">
                      {plante.id}
                    </Button>
                  </td>
                  <td>{plante.planteLibelle}</td>
                  <td>{plante.racine}</td>
                  <td>
                    {plante.photo ? (
                      <div>
                        {plante.photoContentType ? (
                          <a onClick={openFile(plante.photoContentType, plante.photo)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {plante.photoContentType}, {byteSize(plante.photo)}
                        </span>
                      </div>
                    ) : null}
                  </td>

                  <td>{planteTypeNoms[plante.id] ? <Link to={`/type-plante/${plante.nom.id}`}>{planteTypeNoms[plante.id]}</Link> : ''}</td>

                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/plante/${plante.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/plante/${plante.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/plante/${plante.id}/delete`)}
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
              <Translate contentKey="gestionDesFermesApp.plante.home.notFound">No Plantes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Plante;
