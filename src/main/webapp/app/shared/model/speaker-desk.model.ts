export interface ISpeakerDesk {
  id?: number;
  name?: string;
  note?: string;
  imgLink?: string;
  imgContentType?: string;
  img?: any;
  tenantId?: string;
}

export class SpeakerDesk implements ISpeakerDesk {
  constructor(
    public id?: number,
    public name?: string,
    public note?: string,
    public imgLink?: string,
    public imgContentType?: string,
    public img?: any,
    public tenantId?: string
  ) {}
}
