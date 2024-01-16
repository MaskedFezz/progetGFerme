import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITypePlante } from 'app/shared/model/type-plante.model';
import { getEntities as getTypePlantes } from 'app/entities/type-plante/type-plante.reducer';
import { IPlante } from 'app/shared/model/plante.model';
import { getEntity, updateEntity, createEntity, reset } from './plante.reducer';

export const PlanteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const typePlantes = useAppSelector(state => state.typePlante.entities);
  const planteEntity = useAppSelector(state => state.plante.entity);
  const loading = useAppSelector(state => state.plante.loading);
  const updating = useAppSelector(state => state.plante.updating);
  const updateSuccess = useAppSelector(state => state.plante.updateSuccess);

  const handleClose = () => {
    navigate('/plante');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTypePlantes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...planteEntity,
      ...values,
      nom: typePlantes.find(it => it.id.toString() === values.nom.toString()),
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
          ...planteEntity,
          nom: planteEntity?.nom?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionDesFermesApp.plante.home.createOrEditLabel" data-cy="PlanteCreateUpdateHeading">
            <Translate contentKey="gestionDesFermesApp.plante.home.createOrEditLabel">Create or edit a Plante</Translate>
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
                  id="plante-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gestionDesFermesApp.plante.planteLibelle')}
                id="plante-planteLibelle"
                name="planteLibelle"
                data-cy="planteLibelle"
                type="text"
              />
              <ValidatedField
                label={translate('gestionDesFermesApp.plante.racine')}
                id="plante-racine"
                name="racine"
                data-cy="racine"
                type="text"
              />
              <ValidatedBlobField
                label={translate('gestionDesFermesApp.plante.photo')}
                id="plante-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField id="plante-nom" name="nom" data-cy="nom" label={translate('gestionDesFermesApp.plante.nom')} type="select">
                <option value="" key="0" />
                {typePlantes
                  ? typePlantes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nom}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plante" replace color="info">
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

export default PlanteUpdate;
