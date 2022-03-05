import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriod } from 'app/shared/model/period.model';

@Component({
  selector: 'jhi-period-detail',
  templateUrl: './period-detail.component.html',
})
export class PeriodDetailComponent implements OnInit {
  period: IPeriod | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ period }) => (this.period = period));
  }

  previousState(): void {
    window.history.back();
  }
}
