import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IHomeImg, HomeImg } from 'app/shared/model/home-img.model';
import { HomeImgService } from './home-img.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-home-img-update',
  templateUrl: './home-img-update.component.html',
})
export class HomeImgUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    img: [null, [Validators.required]],
    imgContentType: [],
    title: [null, [Validators.required]],
    description: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected homeImgService: HomeImgService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ homeImg }) => {
      this.updateForm(homeImg);
    });
  }

  updateForm(homeImg: IHomeImg): void {
    this.editForm.patchValue({
      id: homeImg.id,
      img: homeImg.img,
      imgContentType: homeImg.imgContentType,
      title: homeImg.title,
      description: homeImg.description,
      tenantId: homeImg.tenantId,
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
    const homeImg = this.createFromForm();
    if (homeImg.id !== undefined) {
      this.subscribeToSaveResponse(this.homeImgService.update(homeImg));
    } else {
      this.subscribeToSaveResponse(this.homeImgService.create(homeImg));
    }
  }

  private createFromForm(): IHomeImg {
    return {
      ...new HomeImg(),
      id: this.editForm.get(['id'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHomeImg>>): void {
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
