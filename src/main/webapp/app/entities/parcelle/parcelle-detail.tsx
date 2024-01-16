import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parcelle.reducer';

export const ParcelleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parcelleEntity = useAppSelector(state => state.parcelle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parcelleDetailsHeading">
          <Translate contentKey="gestionDesFermesApp.parcelle.detail.title">Parcelle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{parcelleEntity.id}</dd>
          <dt>
            <span id="parcelleLibelle">
              <Translate contentKey="gestionDesFermesApp.parcelle.parcelleLibelle">Parcelle Libelle</Translate>
            </span>
          </dt>
          <dd>{parcelleEntity.parcelleLibelle}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="gestionDesFermesApp.parcelle.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {parcelleEntity.photo ? (
              <div>
                {parcelleEntity.photoContentType ? (
                  <a onClick={openFile(parcelleEntity.photoContentType, parcelleEntity.photo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {parcelleEntity.photoContentType}, {byteSize(parcelleEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="gestionDesFermesApp.parcelle.fermeLibelle">Ferme Libelle</Translate>
          </dt>
          <dd>{parcelleEntity.fermeLibelle ? parcelleEntity.fermeLibelle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parcelle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parcelle/${parcelleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParcelleDetail;
