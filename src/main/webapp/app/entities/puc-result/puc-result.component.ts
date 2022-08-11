import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPucResult } from 'app/shared/model/puc-result.model';
import { PucResultService } from './puc-result.service';
import { PucResultDeleteDialogComponent } from './puc-result-delete-dialog.component';

@Component({
  selector: 'jhi-puc-result',
  templateUrl: './puc-result.component.html',
})
export class PucResultComponent implements OnInit, OnDestroy {
  pucResults?: IPucResult[];
  eventSubscriber?: Subscription;

  constructor(protected pucResultService: PucResultService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pucResultService.query().subscribe((res: HttpResponse<IPucResult[]>) => (this.pucResults = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPucResults();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPucResult): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPucResults(): void {
    this.eventSubscriber = this.eventManager.subscribe('pucResultListModification', () => this.loadAll());
  }

  delete(pucResult: IPucResult): void {
    const modalRef = this.modalService.open(PucResultDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pucResult = pucResult;
  }
}
