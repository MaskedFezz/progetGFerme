import { IPlantage } from 'app/shared/model/plantage.model';
import { IFerme } from 'app/shared/model/ferme.model';

export interface IParcelle {
  id?: number;
  parcelleLibelle?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  plantages?: IPlantage[] | null;
  fermeLibelle?: IFerme | null;
}

export const defaultValue: Readonly<IParcelle> = {};
