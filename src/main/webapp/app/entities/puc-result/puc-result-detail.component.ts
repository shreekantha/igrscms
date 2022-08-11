import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPucResult } from 'app/shared/model/puc-result.model';

@Component({
  selector: 'jhi-puc-result-detail',
  templateUrl: './puc-result-detail.component.html',
})
export class PucResultDetailComponent implements OnInit {
  pucResult: IPucResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pucResult }) => (this.pucResult = pucResult));
  }

  previousState(): void {
    window.history.back();
  }
}
