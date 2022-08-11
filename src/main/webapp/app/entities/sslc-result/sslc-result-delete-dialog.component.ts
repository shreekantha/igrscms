import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISslcResult } from 'app/shared/model/sslc-result.model';
import { SslcResultService } from './sslc-result.service';

@Component({
  templateUrl: './sslc-result-delete-dialog.component.html',
})
export class SslcResultDeleteDialogComponent {
  sslcResult?: ISslcResult;

  constructor(
    protected sslcResultService: SslcResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sslcResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sslcResultListModification');
      this.activeModal.close();
    });
  }
}
