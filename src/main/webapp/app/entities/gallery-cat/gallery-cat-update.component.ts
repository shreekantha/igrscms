import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IGalleryCat, GalleryCat } from 'app/shared/model/gallery-cat.model';
import { GalleryCatService } from './gallery-cat.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-gallery-cat-update',
  templateUrl: './gallery-cat-update.component.html',
})
export class GalleryCatUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    imgLink: [null, [Validators.required]],
    img: [null, []],
    imgContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected galleryCatService: GalleryCatService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galleryCat }) => {
      this.updateForm(galleryCat);
    });
  }

  updateForm(galleryCat: IGalleryCat): void {
    this.editForm.patchValue({
      id: galleryCat.id,
      name: galleryCat.name,
      description: galleryCat.description,
      imgLink: galleryCat.imgLink,
      img: galleryCat.img,
      imgContentType: galleryCat.imgContentType,
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
    const galleryCat = this.createFromForm();
    if (galleryCat.id !== undefined) {
      this.subscribeToSaveResponse(this.galleryCatService.update(galleryCat));
    } else {
      this.subscribeToSaveResponse(this.galleryCatService.create(galleryCat));
    }
  }

  private createFromForm(): IGalleryCat {
    return {
      ...new GalleryCat(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGalleryCat>>): void {
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
