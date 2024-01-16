import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ferme from './ferme';
import Parcelle from './parcelle';
import Plante from './plante';
import TypePlante from './type-plante';
import Plantage from './plantage';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="ferme/*" element={<Ferme />} />
        <Route path="parcelle/*" element={<Parcelle />} />
        <Route path="plante/*" element={<Plante />} />
        <Route path="type-plante/*" element={<TypePlante />} />
        <Route path="plantage/*" element={<Plantage />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
