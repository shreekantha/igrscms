export interface IPucResult {
  id?: number;
  academicYear?: string;
  qualityResult?: number;
  topResult?: number;
  tenantId?: string;
}

export class PucResult implements IPucResult {
  constructor(
    public id?: number,
    public academicYear?: string,
    public qualityResult?: number,
    public topResult?: number,
    public tenantId?: string
  ) {}
}
