import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHomeImg } from 'app/shared/model/home-img.model';
import { HomeImgService } from './home-img.service';

@Component({
  templateUrl: './home-img-delete-dialog.component.html',
})
export class HomeImgDeleteDialogComponent {
  homeImg?: IHomeImg;

  constructor(protected homeImgService: HomeImgService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.homeImgService.delete(id).subscribe(() => {
      this.eventManager.broadcast('homeImgListModification');
      this.activeModal.close();
    });
  }
}
