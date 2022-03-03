import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInstitute, Institute } from 'app/shared/model/institute.model';
import { InstituteService } from './institute.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-institute-update',
  templateUrl: './institute-update.component.html',
})
export class InstituteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    shortName: [null, [Validators.required]],
    address: [null, [Validators.required]],
    email: [null, [Validators.required]],
    contact: [null, [Validators.required]],
    logoLink: [null, [Validators.required]],
    logo: [null, []],
    logoContentType: [],
    tagLine: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected instituteService: InstituteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ institute }) => {
      this.updateForm(institute);
    });
  }

  updateForm(institute: IInstitute): void {
    this.editForm.patchValue({
      id: institute.id,
      name: institute.name,
      shortName: institute.shortName,
      address: institute.address,
      email: institute.email,
      contact: institute.contact,
      logoLink: institute.logoLink,
      logo: institute.logo,
      logoContentType: institute.logoContentType,
      tagLine: institute.tagLine,
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
    const institute = this.createFromForm();
    if (institute.id !== undefined) {
      this.subscribeToSaveResponse(this.instituteService.update(institute));
    } else {
      this.subscribeToSaveResponse(this.instituteService.create(institute));
    }
  }

  private createFromForm(): IInstitute {
    return {
      ...new Institute(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      logoLink: this.editForm.get(['logoLink'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      tagLine: this.editForm.get(['tagLine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstitute>>): void {
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
