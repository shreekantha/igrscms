import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAboutUs } from 'app/shared/model/about-us.model';
import { AboutUsService } from './about-us.service';

@Component({
  templateUrl: './about-us-delete-dialog.component.html',
})
export class AboutUsDeleteDialogComponent {
  aboutUs?: IAboutUs;

  constructor(protected aboutUsService: AboutUsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aboutUsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aboutUsListModification');
      this.activeModal.close();
    });
  }
}
