import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriod } from 'app/shared/model/period.model';
import { PeriodService } from './period.service';
import { PeriodDeleteDialogComponent } from './period-delete-dialog.component';

@Component({
  selector: 'jhi-period',
  templateUrl: './period.component.html',
})
export class PeriodComponent implements OnInit, OnDestroy {
  periods?: IPeriod[];
  eventSubscriber?: Subscription;

  constructor(protected periodService: PeriodService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.periodService.query().subscribe((res: HttpResponse<IPeriod[]>) => (this.periods = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeriods();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPeriod): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeriods(): void {
    this.eventSubscriber = this.eventManager.subscribe('periodListModification', () => this.loadAll());
  }

  delete(period: IPeriod): void {
    const modalRef = this.modalService.open(PeriodDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.period = period;
  }
}
