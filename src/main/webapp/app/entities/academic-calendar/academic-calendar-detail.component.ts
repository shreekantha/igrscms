import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademicCalendar } from 'app/shared/model/academic-calendar.model';

@Component({
  selector: 'jhi-academic-calendar-detail',
  templateUrl: './academic-calendar-detail.component.html',
})
export class AcademicCalendarDetailComponent implements OnInit {
  academicCalendar: IAcademicCalendar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academicCalendar }) => (this.academicCalendar = academicCalendar));
  }

  previousState(): void {
    window.history.back();
  }
}
