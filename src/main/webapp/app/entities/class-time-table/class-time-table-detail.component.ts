import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassTimeTable } from 'app/shared/model/class-time-table.model';

@Component({
  selector: 'jhi-class-time-table-detail',
  templateUrl: './class-time-table-detail.component.html',
})
export class ClassTimeTableDetailComponent implements OnInit {
  classTimeTable: IClassTimeTable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classTimeTable }) => (this.classTimeTable = classTimeTable));
  }

  previousState(): void {
    window.history.back();
  }
}
