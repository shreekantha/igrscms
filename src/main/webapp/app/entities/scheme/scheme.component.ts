import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScheme } from 'app/shared/model/scheme.model';
import { SchemeService } from './scheme.service';
import { SchemeDeleteDialogComponent } from './scheme-delete-dialog.component';

@Component({
  selector: 'jhi-scheme',
  templateUrl: './scheme.component.html',
})
export class SchemeComponent implements OnInit, OnDestroy {
  schemes?: IScheme[];
  eventSubscriber?: Subscription;

  constructor(protected schemeService: SchemeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.schemeService.query().subscribe((res: HttpResponse<IScheme[]>) => (this.schemes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSchemes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IScheme): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSchemes(): void {
    this.eventSubscriber = this.eventManager.subscribe('schemeListModification', () => this.loadAll());
  }

  delete(scheme: IScheme): void {
    const modalRef = this.modalService.open(SchemeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scheme = scheme;
  }
}
