import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
// import * as ClassicEditorBuild from '@ckeditor/ckeditor5-build-classic';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { AboutUs, IAboutUs } from 'app/shared/model/about-us.model';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { AboutUsService } from './about-us.service';

@Component({
  selector: 'jhi-about-us-update',
  templateUrl: './about-us-update.component.html',
})
export class AboutUsUpdateComponent implements OnInit {
  isSaving = false;
  // public Editor = ClassicEditorBuild;
  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    description: [null, [Validators.required]],
    imgLink: [],
    img: [null, []],
    imgContentType: [],
    tenantId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected aboutUsService: AboutUsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aboutUs }) => {
      this.updateForm(aboutUs);
    });
  }

  updateForm(aboutUs: IAboutUs): void {
    this.editForm.patchValue({
      id: aboutUs.id,
      title: aboutUs.title,
      description: aboutUs.description,
      imgLink: aboutUs.imgLink,
      img: aboutUs.img,
      imgContentType: aboutUs.imgContentType,
      tenantId: aboutUs.tenantId,
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
    const aboutUs = this.createFromForm();
    if (aboutUs.id !== undefined) {
      this.subscribeToSaveResponse(this.aboutUsService.update(aboutUs));
    } else {
      this.subscribeToSaveResponse(this.aboutUsService.create(aboutUs));
    }
  }

  private createFromForm(): IAboutUs {
    return {
      ...new AboutUs(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAboutUs>>): void {
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
