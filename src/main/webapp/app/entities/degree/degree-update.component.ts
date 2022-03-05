import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDegree, Degree } from 'app/shared/model/degree.model';
import { DegreeService } from './degree.service';

@Component({
  selector: 'jhi-degree-update',
  templateUrl: './degree-update.component.html',
})
export class DegreeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    alias: [null, [Validators.required]],
    graduationType: [null, [Validators.required]],
    type: [null, [Validators.required]],
    numOfYears: [null, [Validators.required]],
    multiBatch: [],
  });

  constructor(protected degreeService: DegreeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ degree }) => {
      this.updateForm(degree);
    });
  }

  updateForm(degree: IDegree): void {
    this.editForm.patchValue({
      id: degree.id,
      name: degree.name,
      alias: degree.alias,
      graduationType: degree.graduationType,
      type: degree.type,
      numOfYears: degree.numOfYears,
      multiBatch: degree.multiBatch,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const degree = this.createFromForm();
    if (degree.id !== undefined) {
      this.subscribeToSaveResponse(this.degreeService.update(degree));
    } else {
      this.subscribeToSaveResponse(this.degreeService.create(degree));
    }
  }

  private createFromForm(): IDegree {
    return {
      ...new Degree(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      alias: this.editForm.get(['alias'])!.value,
      graduationType: this.editForm.get(['graduationType'])!.value,
      type: this.editForm.get(['type'])!.value,
      numOfYears: this.editForm.get(['numOfYears'])!.value,
      multiBatch: this.editForm.get(['multiBatch'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDegree>>): void {
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
