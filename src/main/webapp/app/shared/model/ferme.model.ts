import { IParcelle } from 'app/shared/model/parcelle.model';

export interface IFerme {
  id?: number;
  fermeLibelle?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  parcelles?: IParcelle[] | null;
}

export const defaultValue: Readonly<IFerme> = {};
