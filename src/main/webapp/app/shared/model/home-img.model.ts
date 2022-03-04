export interface IHomeImg {
  id?: number;
  imgContentType?: string;
  img?: any;
  title?: string;
  description?: any;
  active?: boolean;
}

export class HomeImg implements IHomeImg {
  constructor(
    public id?: number,
    public imgContentType?: string,
    public img?: any,
    public title?: string,
    public description?: any,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
