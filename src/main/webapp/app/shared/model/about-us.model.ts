export interface IAboutUs {
  id?: number;
  title?: string;
  description?: any;
  imgLink?: string;
  imgContentType?: string;
  img?: any;
  tenantId?: string;
}

export class AboutUs implements IAboutUs {
  constructor(
    public id?: number,
    public title?: string,
    public description?: any,
    public imgLink?: string,
    public imgContentType?: string,
    public img?: any,
    public tenantId?: string
  ) {}
}
