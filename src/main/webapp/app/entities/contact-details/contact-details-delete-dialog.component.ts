import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactDetails } from 'app/shared/model/contact-details.model';
import { ContactDetailsService } from './contact-details.service';

@Component({
  templateUrl: './contact-details-delete-dialog.component.html',
})
export class ContactDetailsDeleteDialogComponent {
  contactDetails?: IContactDetails;

  constructor(
    protected contactDetailsService: ContactDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactDetailsListModification');
      this.activeModal.close();
    });
  }
}
