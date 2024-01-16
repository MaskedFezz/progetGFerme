import React from 'react';
import { translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import { EntitiesMenu, EntitiesMenuUser } from 'app/entities/menu';

export const EntitiesMenuAdmin = () => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <EntitiesMenu />
  </NavDropdown>
);
export const EntitiesMenuU = () => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <EntitiesMenuUser />
  </NavDropdown>
);
