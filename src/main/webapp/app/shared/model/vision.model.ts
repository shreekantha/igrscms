export interface IVision {
  id?: number;
  detail?: string;
}

export class Vision implements IVision {
  constructor(public id?: number, public detail?: string) {}
}
