export interface IVisionAndMission {
  id?: number;
  vision?: string;
  mission?: string;
  tenantId?: string;
}

export class VisionAndMission implements IVisionAndMission {
  constructor(public id?: number, public vision?: string, public mission?: string, public tenantId?: string) {}
}
