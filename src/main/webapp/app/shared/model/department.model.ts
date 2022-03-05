import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';
import { DegreeOrDeptType } from 'app/shared/model/enumerations/degree-or-dept-type.model';
import { DeptSubType } from 'app/shared/model/enumerations/dept-sub-type.model';

export interface IDepartment {
  id?: number;
  name?: string;
  code?: string;
  alias?: string;
  intake?: number;
  type?: DegreeOrDeptType;
  subType?: DeptSubType;
  classTimeTables?: IClassTimeTable[];
  examTimeTables?: IExamTimeTable[];
  degreeName?: string;
  degreeId?: number;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public alias?: string,
    public intake?: number,
    public type?: DegreeOrDeptType,
    public subType?: DeptSubType,
    public classTimeTables?: IClassTimeTable[],
    public examTimeTables?: IExamTimeTable[],
    public degreeName?: string,
    public degreeId?: number
  ) {}
}
