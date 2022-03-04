export interface INoticeBoard {
  id?: number;
  details?: any;
  active?: boolean;
}

export class NoticeBoard implements INoticeBoard {
  constructor(public id?: number, public details?: any, public active?: boolean) {
    this.active = this.active || false;
  }
}
