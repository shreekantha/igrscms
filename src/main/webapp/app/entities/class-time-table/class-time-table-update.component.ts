import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClassTimeTable, ClassTimeTable } from 'app/shared/model/class-time-table.model';
import { ClassTimeTableService } from './class-time-table.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
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
import { IPeriod } from 'app/shared/model/period.model';
import { PeriodService } from 'app/entities/period/period.service';

type SelectableEntity = IUser | IAcademicCalendar | IDegree | IDepartment | ITerm | ICourse | IPeriod;

@Component({
  selector: 'jhi-class-time-table-update',
  templateUrl: './class-time-table-update.component.html',
})
export class ClassTimeTableUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  academiccalendars: IAcademicCalendar[] = [];
  degrees: IDegree[] = [];
  departments: IDepartment[] = [];
  terms: ITerm[] = [];
  courses: ICourse[] = [];
  periods: IPeriod[] = [];

  editForm = this.fb.group({
    id: [],
    day: [null, [Validators.required]],
    tenantId: [],
    facultyId: [],
    academicCalendarId: [],
    degreeId: [],
    departmentId: [],
    termId: [],
    courseId: [],
    periodId: [],
  });

  constructor(
    protected classTimeTableService: ClassTimeTableService,
    protected userService: UserService,
    protected academicCalendarService: AcademicCalendarService,
    protected degreeService: DegreeService,
    protected departmentService: DepartmentService,
    protected termService: TermService,
    protected courseService: CourseService,
    protected periodService: PeriodService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classTimeTable }) => {
      this.updateForm(classTimeTable);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.academicCalendarService.query().subscribe((res: HttpResponse<IAcademicCalendar[]>) => (this.academiccalendars = res.body || []));

      this.degreeService.query().subscribe((res: HttpResponse<IDegree[]>) => (this.degrees = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.termService.query().subscribe((res: HttpResponse<ITerm[]>) => (this.terms = res.body || []));

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));

      this.periodService.query().subscribe((res: HttpResponse<IPeriod[]>) => (this.periods = res.body || []));
    });
  }

  updateForm(classTimeTable: IClassTimeTable): void {
    this.editForm.patchValue({
      id: classTimeTable.id,
      day: classTimeTable.day,
      tenantId: classTimeTable.tenantId,
      facultyId: classTimeTable.facultyId,
      academicCalendarId: classTimeTable.academicCalendarId,
      degreeId: classTimeTable.degreeId,
      departmentId: classTimeTable.departmentId,
      termId: classTimeTable.termId,
      courseId: classTimeTable.courseId,
      periodId: classTimeTable.periodId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classTimeTable = this.createFromForm();
    if (classTimeTable.id !== undefined) {
      this.subscribeToSaveResponse(this.classTimeTableService.update(classTimeTable));
    } else {
      this.subscribeToSaveResponse(this.classTimeTableService.create(classTimeTable));
    }
  }

  private createFromForm(): IClassTimeTable {
    return {
      ...new ClassTimeTable(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      facultyId: this.editForm.get(['facultyId'])!.value,
      academicCalendarId: this.editForm.get(['academicCalendarId'])!.value,
      degreeId: this.editForm.get(['degreeId'])!.value,
      departmentId: this.editForm.get(['departmentId'])!.value,
      termId: this.editForm.get(['termId'])!.value,
      courseId: this.editForm.get(['courseId'])!.value,
      periodId: this.editForm.get(['periodId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassTimeTable>>): void {
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
