import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPucResult, PucResult } from 'app/shared/model/puc-result.model';
import { PucResultService } from './puc-result.service';

@Component({
  selector: 'jhi-puc-result-update',
  templateUrl: './puc-result-update.component.html',
})
export class PucResultUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    academicYear: [null, [Validators.required]],
    qualityResult: [null, [Validators.required]],
    topResult: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(protected pucResultService: PucResultService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pucResult }) => {
      this.updateForm(pucResult);
    });
  }

  updateForm(pucResult: IPucResult): void {
    this.editForm.patchValue({
      id: pucResult.id,
      academicYear: pucResult.academicYear,
      qualityResult: pucResult.qualityResult,
      topResult: pucResult.topResult,
      tenantId: pucResult.tenantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pucResult = this.createFromForm();
    if (pucResult.id !== undefined) {
      this.subscribeToSaveResponse(this.pucResultService.update(pucResult));
    } else {
      this.subscribeToSaveResponse(this.pucResultService.create(pucResult));
    }
  }

  private createFromForm(): IPucResult {
    return {
      ...new PucResult(),
      id: this.editForm.get(['id'])!.value,
      academicYear: this.editForm.get(['academicYear'])!.value,
      qualityResult: this.editForm.get(['qualityResult'])!.value,
      topResult: this.editForm.get(['topResult'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPucResult>>): void {
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
