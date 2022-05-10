import { Moment } from 'moment';

export interface IAlumniProfile {
  id?: number;
  firstName?: string;
  lastName?: string;
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
  imgContentType?: string;
  img?: any;
  tenantId?: string;
  active?: boolean;
}

export class AlumniProfile implements IAlumniProfile {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
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
    public imgContentType?: string,
    public img?: any,
    public tenantId?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
