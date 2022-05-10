import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVision, Vision } from 'app/shared/model/vision.model';
import { VisionService } from './vision.service';

@Component({
  selector: 'jhi-vision-update',
  templateUrl: './vision-update.component.html',
})
export class VisionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    detail: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(protected visionService: VisionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vision }) => {
      this.updateForm(vision);
    });
  }

  updateForm(vision: IVision): void {
    this.editForm.patchValue({
      id: vision.id,
      detail: vision.detail,
      tenantId: vision.tenantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vision = this.createFromForm();
    if (vision.id !== undefined) {
      this.subscribeToSaveResponse(this.visionService.update(vision));
    } else {
      this.subscribeToSaveResponse(this.visionService.create(vision));
    }
  }

  private createFromForm(): IVision {
    return {
      ...new Vision(),
      id: this.editForm.get(['id'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVision>>): void {
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
