import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISslcResult } from 'app/shared/model/sslc-result.model';
import { SslcResultService } from './sslc-result.service';
import { SslcResultDeleteDialogComponent } from './sslc-result-delete-dialog.component';

@Component({
  selector: 'jhi-sslc-result',
  templateUrl: './sslc-result.component.html',
})
export class SslcResultComponent implements OnInit, OnDestroy {
  sslcResults?: ISslcResult[];
  eventSubscriber?: Subscription;

  constructor(protected sslcResultService: SslcResultService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.sslcResultService.query().subscribe((res: HttpResponse<ISslcResult[]>) => (this.sslcResults = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSslcResults();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISslcResult): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSslcResults(): void {
    this.eventSubscriber = this.eventManager.subscribe('sslcResultListModification', () => this.loadAll());
  }

  delete(sslcResult: ISslcResult): void {
    const modalRef = this.modalService.open(SslcResultDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sslcResult = sslcResult;
  }
}
