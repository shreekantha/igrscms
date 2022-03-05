import { Day } from 'app/shared/model/enumerations/day.model';

export interface IClassTimeTable {
  id?: number;
  day?: Day;
  facultyFirstName?: string;
  facultyId?: number;
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
  periodLabel?: string;
  periodId?: number;
}

export class ClassTimeTable implements IClassTimeTable {
  constructor(
    public id?: number,
    public day?: Day,
    public facultyFirstName?: string,
    public facultyId?: number,
    public academicCalendarAcademicYear?: string,
    public academicCalendarId?: number,
    public degreeName?: string,
    public degreeId?: number,
    public departmentName?: string,
    public departmentId?: number,
    public termTitle?: string,
    public termId?: number,
    public courseName?: string,
    public courseId?: number,
    public periodLabel?: string,
    public periodId?: number
  ) {}
}
