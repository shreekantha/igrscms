import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMission, Mission } from 'app/shared/model/mission.model';
import { MissionService } from './mission.service';

@Component({
  selector: 'jhi-mission-update',
  templateUrl: './mission-update.component.html',
})
export class MissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    details: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(protected missionService: MissionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mission }) => {
      this.updateForm(mission);
    });
  }

  updateForm(mission: IMission): void {
    this.editForm.patchValue({
      id: mission.id,
      details: mission.details,
      tenantId: mission.tenantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mission = this.createFromForm();
    if (mission.id !== undefined) {
      this.subscribeToSaveResponse(this.missionService.update(mission));
    } else {
      this.subscribeToSaveResponse(this.missionService.create(mission));
    }
  }

  private createFromForm(): IMission {
    return {
      ...new Mission(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMission>>): void {
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
