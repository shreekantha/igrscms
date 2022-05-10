export interface IVision {
  id?: number;
  detail?: string;
  tenantId?: string;
}

export class Vision implements IVision {
  constructor(public id?: number, public detail?: string, public tenantId?: string) {}
}
