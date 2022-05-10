export interface INews {
  id?: number;
  title?: string;
  description?: any;
  imgUrl?: string;
  imgContentType?: string;
  img?: any;
  tenantId?: string;
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string,
    public description?: any,
    public imgUrl?: string,
    public imgContentType?: string,
    public img?: any,
    public tenantId?: string
  ) {}
}
