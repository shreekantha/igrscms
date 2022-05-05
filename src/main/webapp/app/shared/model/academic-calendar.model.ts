import { Moment } from 'moment';
import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';

export interface IAcademicCalendar {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  academicYear?: string;
  active?: boolean;
  tenantId?: string;
  classTimeTables?: IClassTimeTable[];
  examTimeTables?: IExamTimeTable[];
  degreeName?: string;
  degreeId?: number;
}

export class AcademicCalendar implements IAcademicCalendar {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public academicYear?: string,
    public active?: boolean,
    public tenantId?: string,
    public classTimeTables?: IClassTimeTable[],
    public examTimeTables?: IExamTimeTable[],
    public degreeName?: string,
    public degreeId?: number
  ) {
    this.active = this.active || false;
  }
}
