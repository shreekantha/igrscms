import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISpeakerDesk, SpeakerDesk } from 'app/shared/model/speaker-desk.model';
import { SpeakerDeskService } from './speaker-desk.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-speaker-desk-update',
  templateUrl: './speaker-desk-update.component.html',
})
export class SpeakerDeskUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    note: [null, [Validators.required]],
    imgLink: [null, [Validators.required]],
    img: [null, []],
    imgContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected speakerDeskService: SpeakerDeskService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ speakerDesk }) => {
      this.updateForm(speakerDesk);
    });
  }

  updateForm(speakerDesk: ISpeakerDesk): void {
    this.editForm.patchValue({
      id: speakerDesk.id,
      name: speakerDesk.name,
      note: speakerDesk.note,
      imgLink: speakerDesk.imgLink,
      img: speakerDesk.img,
      imgContentType: speakerDesk.imgContentType,
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
    const speakerDesk = this.createFromForm();
    if (speakerDesk.id !== undefined) {
      this.subscribeToSaveResponse(this.speakerDeskService.update(speakerDesk));
    } else {
      this.subscribeToSaveResponse(this.speakerDeskService.create(speakerDesk));
    }
  }

  private createFromForm(): ISpeakerDesk {
    return {
      ...new SpeakerDesk(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      note: this.editForm.get(['note'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpeakerDesk>>): void {
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
