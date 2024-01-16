import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plante.reducer';

export const PlanteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planteEntity = useAppSelector(state => state.plante.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planteDetailsHeading">
          <Translate contentKey="gestionDesFermesApp.plante.detail.title">Plante</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planteEntity.id}</dd>
          <dt>
            <span id="planteLibelle">
              <Translate contentKey="gestionDesFermesApp.plante.planteLibelle">Plante Libelle</Translate>
            </span>
          </dt>
          <dd>{planteEntity.planteLibelle}</dd>
          <dt>
            <span id="racine">
              <Translate contentKey="gestionDesFermesApp.plante.racine">Racine</Translate>
            </span>
          </dt>
          <dd>{planteEntity.racine}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="gestionDesFermesApp.plante.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {planteEntity.photo ? (
              <div>
                {planteEntity.photoContentType ? (
                  <a onClick={openFile(planteEntity.photoContentType, planteEntity.photo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {planteEntity.photoContentType}, {byteSize(planteEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="gestionDesFermesApp.plante.nom">Nom</Translate>
          </dt>
          <dd>{planteEntity.nom ? planteEntity.nom.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plante" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plante/${planteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanteDetail;
