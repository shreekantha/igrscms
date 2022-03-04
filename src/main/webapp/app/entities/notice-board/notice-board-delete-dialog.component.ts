import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INoticeBoard } from 'app/shared/model/notice-board.model';
import { NoticeBoardService } from './notice-board.service';

@Component({
  templateUrl: './notice-board-delete-dialog.component.html',
})
export class NoticeBoardDeleteDialogComponent {
  noticeBoard?: INoticeBoard;

  constructor(
    protected noticeBoardService: NoticeBoardService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.noticeBoardService.delete(id).subscribe(() => {
      this.eventManager.broadcast('noticeBoardListModification');
      this.activeModal.close();
    });
  }
}
