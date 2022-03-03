import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstitute } from 'app/shared/model/institute.model';
import { InstituteService } from './institute.service';

@Component({
  templateUrl: './institute-delete-dialog.component.html',
})
export class InstituteDeleteDialogComponent {
  institute?: IInstitute;

  constructor(protected instituteService: InstituteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.instituteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('instituteListModification');
      this.activeModal.close();
    });
  }
}
