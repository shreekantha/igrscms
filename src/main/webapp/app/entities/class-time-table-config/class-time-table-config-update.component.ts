import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClassTimeTableConfig, ClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';
import { ClassTimeTableConfigService } from './class-time-table-config.service';

@Component({
  selector: 'jhi-class-time-table-config-update',
  templateUrl: './class-time-table-config-update.component.html',
})
export class ClassTimeTableConfigUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    timeTableGenType: [null, [Validators.required]],
    tenantId: [],
  });

  constructor(
    protected classTimeTableConfigService: ClassTimeTableConfigService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classTimeTableConfig }) => {
      this.updateForm(classTimeTableConfig);
    });
  }

  updateForm(classTimeTableConfig: IClassTimeTableConfig): void {
    this.editForm.patchValue({
      id: classTimeTableConfig.id,
      timeTableGenType: classTimeTableConfig.timeTableGenType,
      tenantId: classTimeTableConfig.tenantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classTimeTableConfig = this.createFromForm();
    if (classTimeTableConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.classTimeTableConfigService.update(classTimeTableConfig));
    } else {
      this.subscribeToSaveResponse(this.classTimeTableConfigService.create(classTimeTableConfig));
    }
  }

  private createFromForm(): IClassTimeTableConfig {
    return {
      ...new ClassTimeTableConfig(),
      id: this.editForm.get(['id'])!.value,
      timeTableGenType: this.editForm.get(['timeTableGenType'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassTimeTableConfig>>): void {
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
