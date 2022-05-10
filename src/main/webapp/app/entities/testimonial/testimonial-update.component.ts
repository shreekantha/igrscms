import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITestimonial, Testimonial } from 'app/shared/model/testimonial.model';
import { TestimonialService } from './testimonial.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-testimonial-update',
  templateUrl: './testimonial-update.component.html',
})
export class TestimonialUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    img: [null, [Validators.required]],
    imgContentType: [],
    imgLink: [],
    batchYear: [null, [Validators.required]],
    note: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected testimonialService: TestimonialService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testimonial }) => {
      this.updateForm(testimonial);
    });
  }

  updateForm(testimonial: ITestimonial): void {
    this.editForm.patchValue({
      id: testimonial.id,
      name: testimonial.name,
      img: testimonial.img,
      imgContentType: testimonial.imgContentType,
      imgLink: testimonial.imgLink,
      batchYear: testimonial.batchYear,
      note: testimonial.note,
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
    const testimonial = this.createFromForm();
    if (testimonial.id !== undefined) {
      this.subscribeToSaveResponse(this.testimonialService.update(testimonial));
    } else {
      this.subscribeToSaveResponse(this.testimonialService.create(testimonial));
    }
  }

  private createFromForm(): ITestimonial {
    return {
      ...new Testimonial(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      imgLink: this.editForm.get(['imgLink'])!.value,
      batchYear: this.editForm.get(['batchYear'])!.value,
      note: this.editForm.get(['note'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestimonial>>): void {
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
