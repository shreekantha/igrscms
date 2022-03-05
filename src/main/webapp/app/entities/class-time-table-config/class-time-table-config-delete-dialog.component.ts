import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';
import { ClassTimeTableConfigService } from './class-time-table-config.service';

@Component({
  templateUrl: './class-time-table-config-delete-dialog.component.html',
})
export class ClassTimeTableConfigDeleteDialogComponent {
  classTimeTableConfig?: IClassTimeTableConfig;

  constructor(
    protected classTimeTableConfigService: ClassTimeTableConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classTimeTableConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classTimeTableConfigListModification');
      this.activeModal.close();
    });
  }
}
