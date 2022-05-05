import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IExamTimeTable, ExamTimeTable } from 'app/shared/model/exam-time-table.model';
import { ExamTimeTableService } from './exam-time-table.service';
import { IAcademicCalendar } from 'app/shared/model/academic-calendar.model';
import { AcademicCalendarService } from 'app/entities/academic-calendar/academic-calendar.service';
import { IDegree } from 'app/shared/model/degree.model';
import { DegreeService } from 'app/entities/degree/degree.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { ITerm } from 'app/shared/model/term.model';
import { TermService } from 'app/entities/term/term.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';

type SelectableEntity = IAcademicCalendar | IDegree | IDepartment | ITerm | ICourse;

@Component({
  selector: 'jhi-exam-time-table-update',
  templateUrl: './exam-time-table-update.component.html',
})
export class ExamTimeTableUpdateComponent implements OnInit {
  isSaving = false;
  academiccalendars: IAcademicCalendar[] = [];
  degrees: IDegree[] = [];
  departments: IDepartment[] = [];
  terms: ITerm[] = [];
  courses: ICourse[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    examType: [null, [Validators.required]],
    date: [null, [Validators.required]],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    tenantId: [],
    academicCalendarId: [],
    degreeId: [],
    departmentId: [],
    termId: [],
    courseId: [],
  });

  constructor(
    protected examTimeTableService: ExamTimeTableService,
    protected academicCalendarService: AcademicCalendarService,
    protected degreeService: DegreeService,
    protected departmentService: DepartmentService,
    protected termService: TermService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examTimeTable }) => {
      if (!examTimeTable.id) {
        const today = moment().startOf('day');
        examTimeTable.startTime = today;
        examTimeTable.endTime = today;
      }

      this.updateForm(examTimeTable);

      this.academicCalendarService.query().subscribe((res: HttpResponse<IAcademicCalendar[]>) => (this.academiccalendars = res.body || []));

      this.degreeService.query().subscribe((res: HttpResponse<IDegree[]>) => (this.degrees = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.termService.query().subscribe((res: HttpResponse<ITerm[]>) => (this.terms = res.body || []));

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));
    });
  }

  updateForm(examTimeTable: IExamTimeTable): void {
    this.editForm.patchValue({
      id: examTimeTable.id,
      examType: examTimeTable.examType,
      date: examTimeTable.date,
      startTime: examTimeTable.startTime ? examTimeTable.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: examTimeTable.endTime ? examTimeTable.endTime.format(DATE_TIME_FORMAT) : null,
      tenantId: examTimeTable.tenantId,
      academicCalendarId: examTimeTable.academicCalendarId,
      degreeId: examTimeTable.degreeId,
      departmentId: examTimeTable.departmentId,
      termId: examTimeTable.termId,
      courseId: examTimeTable.courseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examTimeTable = this.createFromForm();
    if (examTimeTable.id !== undefined) {
      this.subscribeToSaveResponse(this.examTimeTableService.update(examTimeTable));
    } else {
      this.subscribeToSaveResponse(this.examTimeTableService.create(examTimeTable));
    }
  }

  private createFromForm(): IExamTimeTable {
    return {
      ...new ExamTimeTable(),
      id: this.editForm.get(['id'])!.value,
      examType: this.editForm.get(['examType'])!.value,
      date: this.editForm.get(['date'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value ? moment(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      tenantId: this.editForm.get(['tenantId'])!.value,
      academicCalendarId: this.editForm.get(['academicCalendarId'])!.value,
      degreeId: this.editForm.get(['degreeId'])!.value,
      departmentId: this.editForm.get(['departmentId'])!.value,
      termId: this.editForm.get(['termId'])!.value,
      courseId: this.editForm.get(['courseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamTimeTable>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
