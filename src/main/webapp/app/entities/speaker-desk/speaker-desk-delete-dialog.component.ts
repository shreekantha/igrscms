import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpeakerDesk } from 'app/shared/model/speaker-desk.model';
import { SpeakerDeskService } from './speaker-desk.service';

@Component({
  templateUrl: './speaker-desk-delete-dialog.component.html',
})
export class SpeakerDeskDeleteDialogComponent {
  speakerDesk?: ISpeakerDesk;

  constructor(
    protected speakerDeskService: SpeakerDeskService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.speakerDeskService.delete(id).subscribe(() => {
      this.eventManager.broadcast('speakerDeskListModification');
      this.activeModal.close();
    });
  }
}
