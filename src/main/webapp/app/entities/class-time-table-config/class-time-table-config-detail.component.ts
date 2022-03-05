import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';

@Component({
  selector: 'jhi-class-time-table-config-detail',
  templateUrl: './class-time-table-config-detail.component.html',
})
export class ClassTimeTableConfigDetailComponent implements OnInit {
  classTimeTableConfig: IClassTimeTableConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classTimeTableConfig }) => (this.classTimeTableConfig = classTimeTableConfig));
  }

  previousState(): void {
    window.history.back();
  }
}
