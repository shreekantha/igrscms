import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGallery } from 'app/shared/model/gallery.model';
import { GalleryService } from './gallery.service';

@Component({
  templateUrl: './gallery-delete-dialog.component.html',
})
export class GalleryDeleteDialogComponent {
  gallery?: IGallery;

  constructor(protected galleryService: GalleryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.galleryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('galleryListModification');
      this.activeModal.close();
    });
  }
}
