import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html',
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    designation: [null, [Validators.required]],
    userType: [null, [Validators.required]],
    eduQual: [],
    aboutMe: [null, [Validators.required]],
    hobbies: [],
    aadhaar: [null, [Validators.required, Validators.minLength(12), Validators.maxLength(12)]],
    dob: [null, [Validators.required]],
    mobile: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    email: [null, [Validators.required]],
    imgLink: [],
    img: [null, []],
    imgContentType: [],
    facebookLink: [null, [Validators.required]],
    instaLink: [null, [Validators.required]],
    twitterLink: [null, [Validators.required]],
    linkedLink: [null, [Validators.required]],
    userId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected profileService: ProfileService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      title: profile.title,
      designation: profile.designation,
      userType: profile.userType,
      eduQual: profile.eduQual,
      aboutMe: profile.aboutMe,
      hobbies: profile.hobbies,
      aadhaar: profile.aadhaar,
      dob: profile.dob,
      mobile: profile.mobile,
      email: profile.email,
      imgLink: profile.imgLink,
      img: profile.img,
      imgContentType: profile.imgContentType,
      facebookLink: profile.facebookLink,
      instaLink: profile.instaLink,
      twitterLink: profile.twitterLink,
      linkedLink: profile.linkedLink,
      userId: profile.userId,
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
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      userType: this.editForm.get(['userType'])!.value,
      eduQual: this.editForm.get(['eduQual'])!.value,
      aboutMe: this.editForm.get(['aboutMe'])!.value,
      hobbies: this.editForm.get(['hobbies'])!.value,
      aadhaar: this.editForm.get(['aadhaar'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      email: this.editForm.get(['email'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      facebookLink: this.editForm.get(['facebookLink'])!.value,
      instaLink: this.editForm.get(['instaLink'])!.value,
      twitterLink: this.editForm.get(['twitterLink'])!.value,
      linkedLink: this.editForm.get(['linkedLink'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
