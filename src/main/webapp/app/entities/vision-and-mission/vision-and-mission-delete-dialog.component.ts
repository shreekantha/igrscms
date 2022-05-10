import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVisionAndMission } from 'app/shared/model/vision-and-mission.model';
import { VisionAndMissionService } from './vision-and-mission.service';

@Component({
  templateUrl: './vision-and-mission-delete-dialog.component.html',
})
export class VisionAndMissionDeleteDialogComponent {
  visionAndMission?: IVisionAndMission;

  constructor(
    protected visionAndMissionService: VisionAndMissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.visionAndMissionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('visionAndMissionListModification');
      this.activeModal.close();
    });
  }
}
