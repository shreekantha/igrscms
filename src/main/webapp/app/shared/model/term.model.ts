export interface ITerm {
  id?: number;
  term?: number;
  title?: string;
  description?: string;
  imgUrl?: string;
  imgContentType?: string;
  img?: any;
  noOfStudents?: number;
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
    public classTeacherFirstName?: string,
    public classTeacherId?: number
  ) {}
}
