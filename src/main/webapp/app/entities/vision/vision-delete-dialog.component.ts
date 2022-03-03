import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVision } from 'app/shared/model/vision.model';
import { VisionService } from './vision.service';

@Component({
  templateUrl: './vision-delete-dialog.component.html',
})
export class VisionDeleteDialogComponent {
  vision?: IVision;

  constructor(protected visionService: VisionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.visionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('visionListModification');
      this.activeModal.close();
    });
  }
}
