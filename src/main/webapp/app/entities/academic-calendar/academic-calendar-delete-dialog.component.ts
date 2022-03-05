import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcademicCalendar } from 'app/shared/model/academic-calendar.model';
import { AcademicCalendarService } from './academic-calendar.service';

@Component({
  templateUrl: './academic-calendar-delete-dialog.component.html',
})
export class AcademicCalendarDeleteDialogComponent {
  academicCalendar?: IAcademicCalendar;

  constructor(
    protected academicCalendarService: AcademicCalendarService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.academicCalendarService.delete(id).subscribe(() => {
      this.eventManager.broadcast('academicCalendarListModification');
      this.activeModal.close();
    });
  }
}
