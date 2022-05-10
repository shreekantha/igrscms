export interface IHomeImg {
  id?: number;
  imgContentType?: string;
  img?: any;
  title?: string;
  description?: any;
  tenantId?: string;
}

export class HomeImg implements IHomeImg {
  constructor(
    public id?: number,
    public imgContentType?: string,
    public img?: any,
    public title?: string,
    public description?: any,
    public tenantId?: string
  ) {}
}
