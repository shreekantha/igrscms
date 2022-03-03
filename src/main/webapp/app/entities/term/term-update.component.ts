import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITerm, Term } from 'app/shared/model/term.model';
import { TermService } from './term.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-term-update',
  templateUrl: './term-update.component.html',
})
export class TermUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    term: [null, [Validators.required]],
    title: [null, [Validators.required]],
    description: [],
    imgUrl: [null, [Validators.required]],
    img: [null, []],
    imgContentType: [],
    noOfStudents: [null, [Validators.required]],
    classTeacherId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected termService: TermService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ term }) => {
      this.updateForm(term);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(term: ITerm): void {
    this.editForm.patchValue({
      id: term.id,
      term: term.term,
      title: term.title,
      description: term.description,
      imgUrl: term.imgUrl,
      img: term.img,
      imgContentType: term.imgContentType,
      noOfStudents: term.noOfStudents,
      classTeacherId: term.classTeacherId,
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
    const term = this.createFromForm();
    if (term.id !== undefined) {
      this.subscribeToSaveResponse(this.termService.update(term));
    } else {
      this.subscribeToSaveResponse(this.termService.create(term));
    }
  }

  private createFromForm(): ITerm {
    return {
      ...new Term(),
      id: this.editForm.get(['id'])!.value,
      term: this.editForm.get(['term'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      imgUrl: this.editForm.get(['imgUrl'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      noOfStudents: this.editForm.get(['noOfStudents'])!.value,
      classTeacherId: this.editForm.get(['classTeacherId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITerm>>): void {
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
