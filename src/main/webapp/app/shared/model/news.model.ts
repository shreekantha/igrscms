export interface INews {
  id?: number;
  title?: string;
  description?: string;
  imgUrl?: string;
  imgContentType?: string;
  img?: any;
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public imgUrl?: string,
    public imgContentType?: string,
    public img?: any
  ) {}
}
