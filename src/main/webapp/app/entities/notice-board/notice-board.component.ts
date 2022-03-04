import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INoticeBoard } from 'app/shared/model/notice-board.model';
import { NoticeBoardService } from './notice-board.service';
import { NoticeBoardDeleteDialogComponent } from './notice-board-delete-dialog.component';

@Component({
  selector: 'jhi-notice-board',
  templateUrl: './notice-board.component.html',
})
export class NoticeBoardComponent implements OnInit, OnDestroy {
  noticeBoards?: INoticeBoard[];
  eventSubscriber?: Subscription;

  constructor(
    protected noticeBoardService: NoticeBoardService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.noticeBoardService.query().subscribe((res: HttpResponse<INoticeBoard[]>) => (this.noticeBoards = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNoticeBoards();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INoticeBoard): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInNoticeBoards(): void {
    this.eventSubscriber = this.eventManager.subscribe('noticeBoardListModification', () => this.loadAll());
  }

  delete(noticeBoard: INoticeBoard): void {
    const modalRef = this.modalService.open(NoticeBoardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.noticeBoard = noticeBoard;
  }
}
