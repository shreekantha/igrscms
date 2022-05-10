import { IGallery } from 'app/shared/model/gallery.model';

export interface IGalleryCat {
  id?: number;
  name?: string;
  description?: string;
  imgLink?: string;
  imgContentType?: string;
  img?: any;
  tenantId?: string;
  galleries?: IGallery[];
}

export class GalleryCat implements IGalleryCat {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public imgLink?: string,
    public imgContentType?: string,
    public img?: any,
    public tenantId?: string,
    public galleries?: IGallery[]
  ) {}
}
