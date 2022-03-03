import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGalleryCat } from 'app/shared/model/gallery-cat.model';
import { GalleryCatService } from './gallery-cat.service';

@Component({
  templateUrl: './gallery-cat-delete-dialog.component.html',
})
export class GalleryCatDeleteDialogComponent {
  galleryCat?: IGalleryCat;

  constructor(
    protected galleryCatService: GalleryCatService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.galleryCatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('galleryCatListModification');
      this.activeModal.close();
    });
  }
}
