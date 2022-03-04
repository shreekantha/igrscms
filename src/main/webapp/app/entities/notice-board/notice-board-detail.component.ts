import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INoticeBoard } from 'app/shared/model/notice-board.model';

@Component({
  selector: 'jhi-notice-board-detail',
  templateUrl: './notice-board-detail.component.html',
})
export class NoticeBoardDetailComponent implements OnInit {
  noticeBoard: INoticeBoard | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ noticeBoard }) => (this.noticeBoard = noticeBoard));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
