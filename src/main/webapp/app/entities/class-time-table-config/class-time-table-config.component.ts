import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';
import { ClassTimeTableConfigService } from './class-time-table-config.service';
import { ClassTimeTableConfigDeleteDialogComponent } from './class-time-table-config-delete-dialog.component';

@Component({
  selector: 'jhi-class-time-table-config',
  templateUrl: './class-time-table-config.component.html',
})
export class ClassTimeTableConfigComponent implements OnInit, OnDestroy {
  classTimeTableConfigs?: IClassTimeTableConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected classTimeTableConfigService: ClassTimeTableConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.classTimeTableConfigService
      .query()
      .subscribe((res: HttpResponse<IClassTimeTableConfig[]>) => (this.classTimeTableConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClassTimeTableConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClassTimeTableConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClassTimeTableConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('classTimeTableConfigListModification', () => this.loadAll());
  }

  delete(classTimeTableConfig: IClassTimeTableConfig): void {
    const modalRef = this.modalService.open(ClassTimeTableConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.classTimeTableConfig = classTimeTableConfig;
  }
}
