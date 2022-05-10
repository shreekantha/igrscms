import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IStudentProfile, StudentProfile } from 'app/shared/model/student-profile.model';
import { StudentProfileService } from './student-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-student-profile-update',
  templateUrl: './student-profile-update.component.html',
})
export class StudentProfileUpdateComponent implements OnInit {
  isSaving = false;
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [],
    img: [null, []],
    imgContentType: [],
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
    tenantId: [],
    active: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected studentProfileService: StudentProfileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentProfile }) => {
      this.updateForm(studentProfile);
    });
  }

  updateForm(studentProfile: IStudentProfile): void {
    this.editForm.patchValue({
      id: studentProfile.id,
      firstName: studentProfile.firstName,
      lastName: studentProfile.lastName,
      img: studentProfile.img,
      imgContentType: studentProfile.imgContentType,
      fathersName: studentProfile.fathersName,
      mothersName: studentProfile.mothersName,
      currentTerm: studentProfile.currentTerm,
      joiningAcademicYear: studentProfile.joiningAcademicYear,
      exitAcademicYear: studentProfile.exitAcademicYear,
      mobile: studentProfile.mobile,
      email: studentProfile.email,
      aadhaar: studentProfile.aadhaar,
      dob: studentProfile.dob,
      imgLink: studentProfile.imgLink,
      tenantId: studentProfile.tenantId,
      active: studentProfile.active,
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
    const studentProfile = this.createFromForm();
    if (studentProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.studentProfileService.update(studentProfile));
    } else {
      this.subscribeToSaveResponse(this.studentProfileService.create(studentProfile));
    }
  }

  private createFromForm(): IStudentProfile {
    return {
      ...new StudentProfile(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
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
      tenantId: this.editForm.get(['tenantId'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentProfile>>): void {
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
