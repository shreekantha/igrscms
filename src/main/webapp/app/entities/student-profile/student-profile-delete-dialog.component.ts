import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentProfile } from 'app/shared/model/student-profile.model';
import { StudentProfileService } from './student-profile.service';

@Component({
  templateUrl: './student-profile-delete-dialog.component.html',
})
export class StudentProfileDeleteDialogComponent {
  studentProfile?: IStudentProfile;

  constructor(
    protected studentProfileService: StudentProfileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studentProfileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('studentProfileListModification');
      this.activeModal.close();
    });
  }
}
