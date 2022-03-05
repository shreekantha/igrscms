import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDegree } from 'app/shared/model/degree.model';
import { DegreeService } from './degree.service';

@Component({
  templateUrl: './degree-delete-dialog.component.html',
})
export class DegreeDeleteDialogComponent {
  degree?: IDegree;

  constructor(protected degreeService: DegreeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.degreeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('degreeListModification');
      this.activeModal.close();
    });
  }
}
