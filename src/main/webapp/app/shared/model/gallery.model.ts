export interface IGallery {
  id?: number;
  imgUrl?: string;
  imgContentType?: string;
  img?: any;
  descritpion?: string;
  tenantId?: string;
  categoryName?: string;
  categoryId?: number;
}

export class Gallery implements IGallery {
  constructor(
    public id?: number,
    public imgUrl?: string,
    public imgContentType?: string,
    public img?: any,
    public descritpion?: string,
    public tenantId?: string,
    public categoryName?: string,
    public categoryId?: number
  ) {}
}
