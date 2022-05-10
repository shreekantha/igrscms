import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlumniProfile } from 'app/shared/model/alumni-profile.model';
import { AlumniProfileService } from './alumni-profile.service';

@Component({
  templateUrl: './alumni-profile-delete-dialog.component.html',
})
export class AlumniProfileDeleteDialogComponent {
  alumniProfile?: IAlumniProfile;

  constructor(
    protected alumniProfileService: AlumniProfileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alumniProfileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alumniProfileListModification');
      this.activeModal.close();
    });
  }
}
