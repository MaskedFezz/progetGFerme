import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFerme } from 'app/shared/model/ferme.model';
import { getEntities as getFermes } from 'app/entities/ferme/ferme.reducer';
import { IParcelle } from 'app/shared/model/parcelle.model';
import { getEntity, updateEntity, createEntity, reset } from './parcelle.reducer';

export const ParcelleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fermes = useAppSelector(state => state.ferme.entities);
  const parcelleEntity = useAppSelector(state => state.parcelle.entity);
  const loading = useAppSelector(state => state.parcelle.loading);
  const updating = useAppSelector(state => state.parcelle.updating);
  const updateSuccess = useAppSelector(state => state.parcelle.updateSuccess);

  const handleClose = () => {
    navigate('/parcelle');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFermes({}));
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
      ...parcelleEntity,
      ...values,
      fermeLibelle: fermes.find(it => it.id.toString() === values.fermeLibelle.toString()),
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
          ...parcelleEntity,
          fermeLibelle: parcelleEntity?.fermeLibelle?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionDesFermesApp.parcelle.home.createOrEditLabel" data-cy="ParcelleCreateUpdateHeading">
            <Translate contentKey="gestionDesFermesApp.parcelle.home.createOrEditLabel">Create or edit a Parcelle</Translate>
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
                  id="parcelle-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gestionDesFermesApp.parcelle.parcelleLibelle')}
                id="parcelle-parcelleLibelle"
                name="parcelleLibelle"
                data-cy="parcelleLibelle"
                type="text"
              />
              <ValidatedBlobField
                label={translate('gestionDesFermesApp.parcelle.photo')}
                id="parcelle-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                id="parcelle-fermeLibelle"
                name="fermeLibelle"
                data-cy="fermeLibelle"
                label={translate('gestionDesFermesApp.parcelle.fermeLibelle')}
                type="select"
              >
                <option value="" key="0" />
                {fermes
                  ? fermes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.fermeLibelle}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parcelle" replace color="info">
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

export default ParcelleUpdate;
