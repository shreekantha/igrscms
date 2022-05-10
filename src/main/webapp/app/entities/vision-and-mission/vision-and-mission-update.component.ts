import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVisionAndMission, VisionAndMission } from 'app/shared/model/vision-and-mission.model';
import { VisionAndMissionService } from './vision-and-mission.service';

@Component({
  selector: 'jhi-vision-and-mission-update',
  templateUrl: './vision-and-mission-update.component.html',
})
export class VisionAndMissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    vision: [null, [Validators.required]],
    mission: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(
    protected visionAndMissionService: VisionAndMissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visionAndMission }) => {
      this.updateForm(visionAndMission);
    });
  }

  updateForm(visionAndMission: IVisionAndMission): void {
    this.editForm.patchValue({
      id: visionAndMission.id,
      vision: visionAndMission.vision,
      mission: visionAndMission.mission,
      tenantId: visionAndMission.tenantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const visionAndMission = this.createFromForm();
    if (visionAndMission.id !== undefined) {
      this.subscribeToSaveResponse(this.visionAndMissionService.update(visionAndMission));
    } else {
      this.subscribeToSaveResponse(this.visionAndMissionService.create(visionAndMission));
    }
  }

  private createFromForm(): IVisionAndMission {
    return {
      ...new VisionAndMission(),
      id: this.editForm.get(['id'])!.value,
      vision: this.editForm.get(['vision'])!.value,
      mission: this.editForm.get(['mission'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVisionAndMission>>): void {
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
