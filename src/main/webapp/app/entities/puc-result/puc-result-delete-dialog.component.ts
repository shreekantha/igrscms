import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPucResult } from 'app/shared/model/puc-result.model';
import { PucResultService } from './puc-result.service';

@Component({
  templateUrl: './puc-result-delete-dialog.component.html',
})
export class PucResultDeleteDialogComponent {
  pucResult?: IPucResult;

  constructor(protected pucResultService: PucResultService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pucResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pucResultListModification');
      this.activeModal.close();
    });
  }
}
