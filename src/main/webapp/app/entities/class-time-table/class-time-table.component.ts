import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassTimeTable } from 'app/shared/model/class-time-table.model';
import { ClassTimeTableService } from './class-time-table.service';
import { ClassTimeTableDeleteDialogComponent } from './class-time-table-delete-dialog.component';

@Component({
  selector: 'jhi-class-time-table',
  templateUrl: './class-time-table.component.html',
})
export class ClassTimeTableComponent implements OnInit, OnDestroy {
  classTimeTables?: IClassTimeTable[];
  eventSubscriber?: Subscription;

  constructor(
    protected classTimeTableService: ClassTimeTableService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.classTimeTableService.query().subscribe((res: HttpResponse<IClassTimeTable[]>) => (this.classTimeTables = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClassTimeTables();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClassTimeTable): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClassTimeTables(): void {
    this.eventSubscriber = this.eventManager.subscribe('classTimeTableListModification', () => this.loadAll());
  }

  delete(classTimeTable: IClassTimeTable): void {
    const modalRef = this.modalService.open(ClassTimeTableDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.classTimeTable = classTimeTable;
  }
}
