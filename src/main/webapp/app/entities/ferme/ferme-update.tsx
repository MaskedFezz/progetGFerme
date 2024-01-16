import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFerme } from 'app/shared/model/ferme.model';
import { getEntity, updateEntity, createEntity, reset } from './ferme.reducer';

export const FermeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fermeEntity = useAppSelector(state => state.ferme.entity);
  const loading = useAppSelector(state => state.ferme.loading);
  const updating = useAppSelector(state => state.ferme.updating);
  const updateSuccess = useAppSelector(state => state.ferme.updateSuccess);

  const handleClose = () => {
    navigate('/ferme');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...fermeEntity,
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
      ? {}
      : {
          ...fermeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionDesFermesApp.ferme.home.createOrEditLabel" data-cy="FermeCreateUpdateHeading">
            <Translate contentKey="gestionDesFermesApp.ferme.home.createOrEditLabel">Create or edit a Ferme</Translate>
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
                  id="ferme-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gestionDesFermesApp.ferme.fermeLibelle')}
                id="ferme-fermeLibelle"
                name="fermeLibelle"
                data-cy="fermeLibelle"
                type="text"
              />
              <ValidatedBlobField
                label={translate('gestionDesFermesApp.ferme.photo')}
                id="ferme-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ferme" replace color="info">
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

export default FermeUpdate;
