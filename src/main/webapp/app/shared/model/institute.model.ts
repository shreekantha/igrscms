export interface IInstitute {
  id?: number;
  name?: string;
  shortName?: string;
  address?: string;
  email?: string;
  contact?: string;
  logoLink?: string;
  logoContentType?: string;
  logo?: any;
  tagLine?: string;
}

export class Institute implements IInstitute {
  constructor(
    public id?: number,
    public name?: string,
    public shortName?: string,
    public address?: string,
    public email?: string,
    public contact?: string,
    public logoLink?: string,
    public logoContentType?: string,
    public logo?: any,
    public tagLine?: string
  ) {}
}
