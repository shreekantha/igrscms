import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { INews, News } from 'app/shared/model/news.model';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { NewsService } from './news.service';

@Component({
  selector: 'jhi-news-update',
  templateUrl: './news-update.component.html',
})
export class NewsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    description: [],
    imgUrl: [null, []],
    img: [null, []],
    imgContentType: [],
    tenantId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected newsService: NewsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ news }) => {
      this.updateForm(news);
    });
  }

  updateForm(news: INews): void {
    this.editForm.patchValue({
      id: news.id,
      title: news.title,
      description: news.description,
      imgUrl: news.imgUrl,
      img: news.img,
      imgContentType: news.imgContentType,
      tenantId: news.tenantId,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const news = this.createFromForm();
    if (news.id !== undefined) {
      this.subscribeToSaveResponse(this.newsService.update(news));
    } else {
      this.subscribeToSaveResponse(this.newsService.create(news));
    }
  }

  private createFromForm(): INews {
    return {
      ...new News(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      imgUrl: this.editForm.get(['imgUrl'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INews>>): void {
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
