import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-user-profile-update',
  templateUrl: './user-profile-update.component.html',
})
export class UserProfileUpdateComponent implements OnInit {
  isSaving = false;
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    lastName: [],
    mobile: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    email: [null, [Validators.required]],
    designation: [null, [Validators.required]],
    userType: [null, [Validators.required]],
    eduQual: [],
    aboutMe: [],
    hobbies: [],
    aadhaar: [null, [Validators.minLength(12), Validators.maxLength(12)]],
    dob: [null, [Validators.required]],
    imgLink: [],
    img: [null, []],
    imgContentType: [],
    facebookLink: [],
    instaLink: [],
    twitterLink: [],
    linkedLink: [],
    tenantId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected userProfileService: UserProfileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.updateForm(userProfile);
    });
  }

  updateForm(userProfile: IUserProfile): void {
    this.editForm.patchValue({
      id: userProfile.id,
      title: userProfile.title,
      firstName: userProfile.firstName,
      lastName: userProfile.lastName,
      mobile: userProfile.mobile,
      email: userProfile.email,
      designation: userProfile.designation,
      userType: userProfile.userType,
      eduQual: userProfile.eduQual,
      aboutMe: userProfile.aboutMe,
      hobbies: userProfile.hobbies,
      aadhaar: userProfile.aadhaar,
      dob: userProfile.dob,
      imgLink: userProfile.imgLink,
      img: userProfile.img,
      imgContentType: userProfile.imgContentType,
      facebookLink: userProfile.facebookLink,
      instaLink: userProfile.instaLink,
      twitterLink: userProfile.twitterLink,
      linkedLink: userProfile.linkedLink,
      tenantId: userProfile.tenantId,
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
    const userProfile = this.createFromForm();
    if (userProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileService.update(userProfile));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(userProfile));
    }
  }

  private createFromForm(): IUserProfile {
    return {
      ...new UserProfile(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      email: this.editForm.get(['email'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      userType: this.editForm.get(['userType'])!.value,
      eduQual: this.editForm.get(['eduQual'])!.value,
      aboutMe: this.editForm.get(['aboutMe'])!.value,
      hobbies: this.editForm.get(['hobbies'])!.value,
      aadhaar: this.editForm.get(['aadhaar'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      facebookLink: this.editForm.get(['facebookLink'])!.value,
      instaLink: this.editForm.get(['instaLink'])!.value,
      twitterLink: this.editForm.get(['twitterLink'])!.value,
      linkedLink: this.editForm.get(['linkedLink'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>): void {
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
