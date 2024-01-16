import ferme from 'app/entities/ferme/ferme.reducer';
import parcelle from 'app/entities/parcelle/parcelle.reducer';
import plante from 'app/entities/plante/plante.reducer';
import typePlante from 'app/entities/type-plante/type-plante.reducer';
import plantage from 'app/entities/plantage/plantage.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  ferme,
  parcelle,
  plante,
  typePlante,
  plantage,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
