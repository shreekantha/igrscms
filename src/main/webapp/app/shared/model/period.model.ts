import { IClassTimeTable } from 'app/shared/model/class-time-table.model';

export interface IPeriod {
  id?: number;
  number?: number;
  label?: string;
  startTime?: string;
  endTime?: string;
  tenantId?: string;
  classTimeTables?: IClassTimeTable[];
}

export class Period implements IPeriod {
  constructor(
    public id?: number,
    public number?: number,
    public label?: string,
    public startTime?: string,
    public endTime?: string,
    public tenantId?: string,
    public classTimeTables?: IClassTimeTable[]
  ) {}
}
