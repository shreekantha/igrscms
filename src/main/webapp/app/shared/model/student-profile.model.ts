import { Moment } from 'moment';

export interface IStudentProfile {
  id?: number;
  firstName?: string;
  lastName?: string;
  imgContentType?: string;
  img?: any;
  fathersName?: string;
  mothersName?: string;
  currentTerm?: number;
  joiningAcademicYear?: string;
  exitAcademicYear?: string;
  mobile?: string;
  email?: string;
  aadhaar?: string;
  dob?: Moment;
  imgLink?: string;
  tenantId?: string;
  active?: boolean;
}

export class StudentProfile implements IStudentProfile {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public imgContentType?: string,
    public img?: any,
    public fathersName?: string,
    public mothersName?: string,
    public currentTerm?: number,
    public joiningAcademicYear?: string,
    public exitAcademicYear?: string,
    public mobile?: string,
    public email?: string,
    public aadhaar?: string,
    public dob?: Moment,
    public imgLink?: string,
    public tenantId?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
