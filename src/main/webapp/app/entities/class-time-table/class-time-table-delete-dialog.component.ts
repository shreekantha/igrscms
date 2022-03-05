import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { ClassTimeTableService } from './class-time-table.service';

@Component({
  templateUrl: './class-time-table-delete-dialog.component.html',
})
export class ClassTimeTableDeleteDialogComponent {
  classTimeTable?: IClassTimeTable;

  constructor(
    protected classTimeTableService: ClassTimeTableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classTimeTableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classTimeTableListModification');
      this.activeModal.close();
    });
  }
}
