import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPeriod, Period } from 'app/shared/model/period.model';
import { PeriodService } from './period.service';

@Component({
  selector: 'jhi-period-update',
  templateUrl: './period-update.component.html',
})
export class PeriodUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required]],
    label: [null, [Validators.required]],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
  });

  constructor(protected periodService: PeriodService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ period }) => {
      this.updateForm(period);
    });
  }

  updateForm(period: IPeriod): void {
    this.editForm.patchValue({
      id: period.id,
      number: period.number,
      label: period.label,
      startTime: period.startTime,
      endTime: period.endTime,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const period = this.createFromForm();
    if (period.id !== undefined) {
      this.subscribeToSaveResponse(this.periodService.update(period));
    } else {
      this.subscribeToSaveResponse(this.periodService.create(period));
    }
  }

  private createFromForm(): IPeriod {
    return {
      ...new Period(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      label: this.editForm.get(['label'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      endTime: this.editForm.get(['endTime'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriod>>): void {
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
