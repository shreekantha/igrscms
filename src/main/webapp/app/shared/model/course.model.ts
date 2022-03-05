import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';

export interface ICourse {
  id?: number;
  name?: string;
  alias?: string;
  code?: string;
  classTimeTables?: IClassTimeTable[];
  examTimeTables?: IExamTimeTable[];
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public name?: string,
    public alias?: string,
    public code?: string,
    public classTimeTables?: IClassTimeTable[],
    public examTimeTables?: IExamTimeTable[]
  ) {}
}
