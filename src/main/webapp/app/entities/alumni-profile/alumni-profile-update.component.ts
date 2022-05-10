import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAlumniProfile, AlumniProfile } from 'app/shared/model/alumni-profile.model';
import { AlumniProfileService } from './alumni-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-alumni-profile-update',
  templateUrl: './alumni-profile-update.component.html',
})
export class AlumniProfileUpdateComponent implements OnInit {
  isSaving = false;
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [],
    fathersName: [],
    mothersName: [],
    currentTerm: [],
    joiningAcademicYear: [],
    exitAcademicYear: [],
    mobile: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    email: [],
    aadhaar: [null, [Validators.minLength(12), Validators.maxLength(12)]],
    dob: [null, [Validators.required]],
    imgLink: [],
    img: [null, []],
    imgContentType: [],
    tenantId: [],
    active: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected alumniProfileService: AlumniProfileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alumniProfile }) => {
      this.updateForm(alumniProfile);
    });
  }

  updateForm(alumniProfile: IAlumniProfile): void {
    this.editForm.patchValue({
      id: alumniProfile.id,
      firstName: alumniProfile.firstName,
      lastName: alumniProfile.lastName,
      fathersName: alumniProfile.fathersName,
      mothersName: alumniProfile.mothersName,
      currentTerm: alumniProfile.currentTerm,
      joiningAcademicYear: alumniProfile.joiningAcademicYear,
      exitAcademicYear: alumniProfile.exitAcademicYear,
      mobile: alumniProfile.mobile,
      email: alumniProfile.email,
      aadhaar: alumniProfile.aadhaar,
      dob: alumniProfile.dob,
      imgLink: alumniProfile.imgLink,
      img: alumniProfile.img,
      imgContentType: alumniProfile.imgContentType,
      tenantId: alumniProfile.tenantId,
      active: alumniProfile.active,
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
    const alumniProfile = this.createFromForm();
    if (alumniProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.alumniProfileService.update(alumniProfile));
    } else {
      this.subscribeToSaveResponse(this.alumniProfileService.create(alumniProfile));
    }
  }

  private createFromForm(): IAlumniProfile {
    return {
      ...new AlumniProfile(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      fathersName: this.editForm.get(['fathersName'])!.value,
      mothersName: this.editForm.get(['mothersName'])!.value,
      currentTerm: this.editForm.get(['currentTerm'])!.value,
      joiningAcademicYear: this.editForm.get(['joiningAcademicYear'])!.value,
      exitAcademicYear: this.editForm.get(['exitAcademicYear'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      email: this.editForm.get(['email'])!.value,
      aadhaar: this.editForm.get(['aadhaar'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlumniProfile>>): void {
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
