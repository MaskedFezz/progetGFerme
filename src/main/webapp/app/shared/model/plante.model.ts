import { IPlantage } from 'app/shared/model/plantage.model';
import { ITypePlante } from 'app/shared/model/type-plante.model';

export interface IPlante {
  id?: number;
  planteLibelle?: string | null;
  racine?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  plantages?: IPlantage[] | null;
  nom?: ITypePlante | null;
}

export const defaultValue: Readonly<IPlante> = {};
