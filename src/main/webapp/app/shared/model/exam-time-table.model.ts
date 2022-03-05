import { Moment } from 'moment';
import { ExamType } from 'app/shared/model/enumerations/exam-type.model';

export interface IExamTimeTable {
  id?: number;
  examType?: ExamType;
  date?: Moment;
  startTime?: Moment;
  endTime?: Moment;
  academicCalendarAcademicYear?: string;
  academicCalendarId?: number;
  degreeName?: string;
  degreeId?: number;
  departmentName?: string;
  departmentId?: number;
  termTitle?: string;
  termId?: number;
  courseName?: string;
  courseId?: number;
}

export class ExamTimeTable implements IExamTimeTable {
  constructor(
    public id?: number,
    public examType?: ExamType,
    public date?: Moment,
    public startTime?: Moment,
    public endTime?: Moment,
    public academicCalendarAcademicYear?: string,
    public academicCalendarId?: number,
    public degreeName?: string,
    public degreeId?: number,
    public departmentName?: string,
    public departmentId?: number,
    public termTitle?: string,
    public termId?: number,
    public courseName?: string,
    public courseId?: number
  ) {}
}
