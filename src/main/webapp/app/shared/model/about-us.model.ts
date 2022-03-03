export interface IAboutUs {
  id?: number;
  title?: string;
  description?: string;
  imgLink?: string;
  imgContentType?: string;
  img?: any;
}

export class AboutUs implements IAboutUs {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public imgLink?: string,
    public imgContentType?: string,
    public img?: any
  ) {}
}
