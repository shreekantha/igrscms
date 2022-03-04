import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { INoticeBoard, NoticeBoard } from 'app/shared/model/notice-board.model';
import { NoticeBoardService } from './notice-board.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-notice-board-update',
  templateUrl: './notice-board-update.component.html',
})
export class NoticeBoardUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    details: [null, [Validators.required]],
    active: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected noticeBoardService: NoticeBoardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ noticeBoard }) => {
      this.updateForm(noticeBoard);
    });
  }

  updateForm(noticeBoard: INoticeBoard): void {
    this.editForm.patchValue({
      id: noticeBoard.id,
      details: noticeBoard.details,
      active: noticeBoard.active,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('igrscmsApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const noticeBoard = this.createFromForm();
    if (noticeBoard.id !== undefined) {
      this.subscribeToSaveResponse(this.noticeBoardService.update(noticeBoard));
    } else {
      this.subscribeToSaveResponse(this.noticeBoardService.create(noticeBoard));
    }
  }

  private createFromForm(): INoticeBoard {
    return {
      ...new NoticeBoard(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INoticeBoard>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
