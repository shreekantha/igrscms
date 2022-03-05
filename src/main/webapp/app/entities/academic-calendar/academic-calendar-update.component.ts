import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAcademicCalendar, AcademicCalendar } from 'app/shared/model/academic-calendar.model';
import { AcademicCalendarService } from './academic-calendar.service';
import { IDegree } from 'app/shared/model/degree.model';
import { DegreeService } from 'app/entities/degree/degree.service';

@Component({
  selector: 'jhi-academic-calendar-update',
  templateUrl: './academic-calendar-update.component.html',
})
export class AcademicCalendarUpdateComponent implements OnInit {
  isSaving = false;
  degrees: IDegree[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    academicYear: [null, [Validators.required]],
    active: [null, [Validators.required]],
    degreeId: [],
  });

  constructor(
    protected academicCalendarService: AcademicCalendarService,
    protected degreeService: DegreeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academicCalendar }) => {
      this.updateForm(academicCalendar);

      this.degreeService.query().subscribe((res: HttpResponse<IDegree[]>) => (this.degrees = res.body || []));
    });
  }

  updateForm(academicCalendar: IAcademicCalendar): void {
    this.editForm.patchValue({
      id: academicCalendar.id,
      startDate: academicCalendar.startDate,
      endDate: academicCalendar.endDate,
      academicYear: academicCalendar.academicYear,
      active: academicCalendar.active,
      degreeId: academicCalendar.degreeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const academicCalendar = this.createFromForm();
    if (academicCalendar.id !== undefined) {
      this.subscribeToSaveResponse(this.academicCalendarService.update(academicCalendar));
    } else {
      this.subscribeToSaveResponse(this.academicCalendarService.create(academicCalendar));
    }
  }

  private createFromForm(): IAcademicCalendar {
    return {
      ...new AcademicCalendar(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      academicYear: this.editForm.get(['academicYear'])!.value,
      active: this.editForm.get(['active'])!.value,
      degreeId: this.editForm.get(['degreeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicCalendar>>): void {
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

  trackById(index: number, item: IDegree): any {
    return item.id;
  }
}
