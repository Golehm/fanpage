import { Era } from 'app/shared/model/enumerations/era.model';
import { Kind } from 'app/shared/model/enumerations/kind.model';

export interface ICulture {
  id?: number;
  era?: Era;
  kind?: Kind;
  imageContentType?: string;
  image?: any;
  name?: string;
  unit?: string;
  unitDescription?: string;
  quarter?: string;
  quarterDescription?: string;
}

export class Culture implements ICulture {
  constructor(
    public id?: number,
    public era?: Era,
    public kind?: Kind,
    public imageContentType?: string,
    public image?: any,
    public name?: string,
    public unit?: string,
    public unitDescription?: string,
    public quarter?: string,
    public quarterDescription?: string
  ) {}
}
