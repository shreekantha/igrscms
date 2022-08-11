import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISslcResult, SslcResult } from 'app/shared/model/sslc-result.model';
import { SslcResultService } from './sslc-result.service';

@Component({
  selector: 'jhi-sslc-result-update',
  templateUrl: './sslc-result-update.component.html',
})
export class SslcResultUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    academicYear: [null, [Validators.required]],
    qualityResult: [null, [Validators.required]],
    topResult: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(protected sslcResultService: SslcResultService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sslcResult }) => {
      this.updateForm(sslcResult);
    });
  }

  updateForm(sslcResult: ISslcResult): void {
    this.editForm.patchValue({
      id: sslcResult.id,
      academicYear: sslcResult.academicYear,
      qualityResult: sslcResult.qualityResult,
      topResult: sslcResult.topResult,
      tenantId: sslcResult.tenantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sslcResult = this.createFromForm();
    if (sslcResult.id !== undefined) {
      this.subscribeToSaveResponse(this.sslcResultService.update(sslcResult));
    } else {
      this.subscribeToSaveResponse(this.sslcResultService.create(sslcResult));
    }
  }

  private createFromForm(): ISslcResult {
    return {
      ...new SslcResult(),
      id: this.editForm.get(['id'])!.value,
      academicYear: this.editForm.get(['academicYear'])!.value,
      qualityResult: this.editForm.get(['qualityResult'])!.value,
      topResult: this.editForm.get(['topResult'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISslcResult>>): void {
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
