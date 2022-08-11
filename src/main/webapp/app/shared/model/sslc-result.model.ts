export interface ISslcResult {
  id?: number;
  academicYear?: string;
  qualityResult?: number;
  topResult?: number;
  tenantId?: string;
}

export class SslcResult implements ISslcResult {
  constructor(
    public id?: number,
    public academicYear?: string,
    public qualityResult?: number,
    public topResult?: number,
    public tenantId?: string
  ) {}
}
