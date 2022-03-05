import { IDepartment } from 'app/shared/model/department.model';
import { IAcademicCalendar } from 'app/shared/model/academic-calendar.model';
import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';
import { GraduationType } from 'app/shared/model/enumerations/graduation-type.model';
import { DegreeOrDeptType } from 'app/shared/model/enumerations/degree-or-dept-type.model';

export interface IDegree {
  id?: number;
  name?: string;
  alias?: string;
  graduationType?: GraduationType;
  type?: DegreeOrDeptType;
  numOfYears?: number;
  multiBatch?: boolean;
  departments?: IDepartment[];
  academicCalendars?: IAcademicCalendar[];
  classTimeTables?: IClassTimeTable[];
  examTimeTables?: IExamTimeTable[];
}

export class Degree implements IDegree {
  constructor(
    public id?: number,
    public name?: string,
    public alias?: string,
    public graduationType?: GraduationType,
    public type?: DegreeOrDeptType,
    public numOfYears?: number,
    public multiBatch?: boolean,
    public departments?: IDepartment[],
    public academicCalendars?: IAcademicCalendar[],
    public classTimeTables?: IClassTimeTable[],
    public examTimeTables?: IExamTimeTable[]
  ) {
    this.multiBatch = this.multiBatch || false;
  }
}
