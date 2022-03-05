export interface IScheme {
  id?: number;
  name?: string;
  startedAcademicYear?: string;
  year?: number;
}

export class Scheme implements IScheme {
  constructor(public id?: number, public name?: string, public startedAcademicYear?: string, public year?: number) {}
}
