import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IGallery, Gallery } from 'app/shared/model/gallery.model';
import { GalleryService } from './gallery.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IGalleryCat } from 'app/shared/model/gallery-cat.model';
import { GalleryCatService } from 'app/entities/gallery-cat/gallery-cat.service';

@Component({
  selector: 'jhi-gallery-update',
  templateUrl: './gallery-update.component.html',
})
export class GalleryUpdateComponent implements OnInit {
  isSaving = false;
  gallerycats: IGalleryCat[] = [];

  editForm = this.fb.group({
    id: [],
    imgUrl: [null, [Validators.required]],
    img: [null, []],
    imgContentType: [],
    descritpion: [],
    tenantId: [],
    categoryId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected galleryService: GalleryService,
    protected galleryCatService: GalleryCatService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gallery }) => {
      this.updateForm(gallery);

      this.galleryCatService.query().subscribe((res: HttpResponse<IGalleryCat[]>) => (this.gallerycats = res.body || []));
    });
  }

  updateForm(gallery: IGallery): void {
    this.editForm.patchValue({
      id: gallery.id,
      imgUrl: gallery.imgUrl,
      img: gallery.img,
      imgContentType: gallery.imgContentType,
      descritpion: gallery.descritpion,
      tenantId: gallery.tenantId,
      categoryId: gallery.categoryId,
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
    const gallery = this.createFromForm();
    if (gallery.id !== undefined) {
      this.subscribeToSaveResponse(this.galleryService.update(gallery));
    } else {
      this.subscribeToSaveResponse(this.galleryService.create(gallery));
    }
  }

  private createFromForm(): IGallery {
    return {
      ...new Gallery(),
      id: this.editForm.get(['id'])!.value,
      imgUrl: this.editForm.get(['imgUrl'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      descritpion: this.editForm.get(['descritpion'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGallery>>): void {
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

  trackById(index: number, item: IGalleryCat): any {
    return item.id;
  }
}
