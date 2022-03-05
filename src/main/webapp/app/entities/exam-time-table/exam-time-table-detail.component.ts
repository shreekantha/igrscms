import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';

@Component({
  selector: 'jhi-exam-time-table-detail',
  templateUrl: './exam-time-table-detail.component.html',
})
export class ExamTimeTableDetailComponent implements OnInit {
  examTimeTable: IExamTimeTable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examTimeTable }) => (this.examTimeTable = examTimeTable));
  }

  previousState(): void {
    window.history.back();
  }
}
