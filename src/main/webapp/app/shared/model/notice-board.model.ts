export interface INoticeBoard {
  id?: number;
  details?: any;
  active?: boolean;
  tenantId?: string;
}

export class NoticeBoard implements INoticeBoard {
  constructor(public id?: number, public details?: any, public active?: boolean, public tenantId?: string) {
    this.active = this.active || false;
  }
}
