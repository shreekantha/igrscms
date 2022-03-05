import { TimeTableGenType } from 'app/shared/model/enumerations/time-table-gen-type.model';

export interface IClassTimeTableConfig {
  id?: number;
  timeTableGenType?: TimeTableGenType;
}

export class ClassTimeTableConfig implements IClassTimeTableConfig {
  constructor(public id?: number, public timeTableGenType?: TimeTableGenType) {}
}
