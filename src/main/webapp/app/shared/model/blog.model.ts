export interface IBlog {
  id?: number;
  title?: string;
  description?: string;
  content?: any;
  tenantId?: string;
  userFirstName?: string;
  userId?: number;
}

export class Blog implements IBlog {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public content?: any,
    public tenantId?: string,
    public userFirstName?: string,
    public userId?: number
  ) {}
}
