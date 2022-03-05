import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';
import { ExamTimeTableService } from './exam-time-table.service';

@Component({
  templateUrl: './exam-time-table-delete-dialog.component.html',
})
export class ExamTimeTableDeleteDialogComponent {
  examTimeTable?: IExamTimeTable;

  constructor(
    protected examTimeTableService: ExamTimeTableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examTimeTableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examTimeTableListModification');
      this.activeModal.close();
    });
  }
}
