export interface IContactDetails {
  id?: number;
  address?: string;
  email?: string;
  contact?: string;
  mapLink?: any;
  tenantId?: string;
}

export class ContactDetails implements IContactDetails {
  constructor(
    public id?: number,
    public address?: string,
    public email?: string,
    public contact?: string,
    public mapLink?: any,
    public tenantId?: string
  ) {}
}
