import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';

export interface ITerm {
  id?: number;
  term?: number;
  title?: string;
  description?: string;
  imgUrl?: string;
  imgContentType?: string;
  img?: any;
  noOfStudents?: number;
  tenantId?: string;
  classTimeTables?: IClassTimeTable[];
  examTimeTables?: IExamTimeTable[];
  classTeacherFirstName?: string;
  classTeacherId?: number;
}

export class Term implements ITerm {
  constructor(
    public id?: number,
    public term?: number,
    public title?: string,
    public description?: string,
    public imgUrl?: string,
    public imgContentType?: string,
    public img?: any,
    public noOfStudents?: number,
    public tenantId?: string,
    public classTimeTables?: IClassTimeTable[],
    public examTimeTables?: IExamTimeTable[],
    public classTeacherFirstName?: string,
    public classTeacherId?: number
  ) {}
}
