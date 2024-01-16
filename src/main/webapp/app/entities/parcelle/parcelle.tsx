import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './parcelle.reducer';

export const Parcelle = () => {
  const dispatch = useAppDispatch();
  const [fermeLibelles, setfermeLibelles] = useState({});

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const parcelleList = useAppSelector(state => state.parcelle.entities);
  const loading = useAppSelector(state => state.parcelle.loading);

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
        parcelleList.map(async parcelle => {
          const fermeLibelleResponse = await fetch(`/api/parcelles/${parcelle.id}//ferme-libelle`);

          const planteTypeNom = await fermeLibelleResponse.text();
          setfermeLibelles(prevState => ({ ...prevState, [parcelle.id]: planteTypeNom }));
        }),
      );
    };

    fetchPlantTypeNames();
  }, [parcelleList]);

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

  const filteredParcelleList = parcelleList.filter(parcelle => {
    const searchLower = searchTerm.toLowerCase();

    return (
      parcelle.parcelleLibelle.toLowerCase().includes(searchLower) ||
      // Ajoutez d'autres champs si nécessaire
      fermeLibelles[parcelle.id].toLowerCase().includes(searchLower)
    );
  });

  return (
    <div>
      <h2 id="parcelle-heading" data-cy="ParcelleHeading">
        <Translate contentKey="gestionDesFermesApp.parcelle.home.title">Parcelles</Translate>

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
            <Translate contentKey="gestionDesFermesApp.parcelle.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/parcelle/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gestionDesFermesApp.parcelle.home.createLabel">Create new Parcelle</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {filteredParcelleList && filteredParcelleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="gestionDesFermesApp.parcelle.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('parcelleLibelle')}>
                  <Translate contentKey="gestionDesFermesApp.parcelle.parcelleLibelle">Parcelle </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('parcelleLibelle')} />
                </th>
                <th className="hand" onClick={sort('photo')}>
                  <Translate contentKey="gestionDesFermesApp.parcelle.photo">Photo</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('photo')} />
                </th>
                <th>
                  <Translate contentKey="gestionDesFermesApp.parcelle.fermeLibelle">Ferme Libelle</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredParcelleList.map((parcelle, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/parcelle/${parcelle.id}`} color="link" size="sm">
                      {parcelle.id}
                    </Button>
                  </td>
                  <td>{parcelle.parcelleLibelle}</td>

                  <td>
                    {parcelle.photo ? (
                      <div>
                        {parcelle.photoContentType ? (
                          <a onClick={openFile(parcelle.photoContentType, parcelle.photo)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {parcelle.photoContentType}, {byteSize(parcelle.photo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <td>
                      -{parcelle.fermeLibelle ? <Link to={`/ferme/${parcelle.fermeLibelle.id}`}>{parcelle.fermeLibelle.id}</Link> : ''}-
                      {fermeLibelles[parcelle.id] ? (
                        <Link to={`/ferme/${parcelle.fermeLibelle.id}`}>{fermeLibelles[parcelle.id]}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/parcelle/${parcelle.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/parcelle/${parcelle.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/parcelle/${parcelle.id}/delete`)}
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
              <Translate contentKey="gestionDesFermesApp.parcelle.home.notFound">No Parcelles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Parcelle;
